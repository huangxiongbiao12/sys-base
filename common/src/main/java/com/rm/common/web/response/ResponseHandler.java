package com.rm.common.web.response;

import com.alibaba.fastjson.JSON;
import com.rm.common.web.exception.ResponseException;
import com.rm.common.web.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author huangxiongbiao
 * @Description
 * @Date 2019/6/4 上午8:55
 */
@RestControllerAdvice
@Slf4j
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    private static final Pattern METHOD_PATTERN = Pattern.compile(
            "^\\w*(Save|Insert|Add|Delete|Remove|Set|Edit|Update)\\w*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern SWAGGER_PATTERN = Pattern.compile(
            "(?:^springfox\\.|io\\.swagger\\.).*");

    @Value("${rm.originResult:prometheus}")
    String originResult;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if ((HttpStatus.OK.value() !=
                ((ServletServerHttpResponse) serverHttpResponse)
                        .getServletResponse().getStatus())
                || object instanceof Result || object instanceof Resource
                || serverHttpRequest.getURI().getPath().contains(originResult)) {
            return object;
        }

        final Method method = methodParameter.getMethod();
        // 注解接口不做结果拦截处理
        if (method.getDeclaringClass().getAnnotation(OriginResult.class) != null
                || method.getAnnotation(OriginResult.class) != null) {
            return object;
        }
        if (object == null) return defaultReturn(method.getReturnType());

        // 放过所有swagger家族的成员，惹不起惹不起
        if (SWAGGER_PATTERN
                .matcher(methodParameter
                        .getDeclaringClass().getName())
                .matches()) {
            return object;
        }
        // 操作类型
        String oper = "";
        // 获取当前处理请求的controller的方法
        Matcher m = METHOD_PATTERN.matcher(method.getName());
        // 尝试匹配关键字
        if (m.matches()) {
            // 匹配成功转为相应中文信息，表示当前方法的操作类型
            switch (m.group(1).toUpperCase()) {
                case "INSERT":
                case "SAVE":
                case "ADD":
                    oper = "新增";
                    break;

                case "REMOVE":
                case "DELETE":
                    oper = "删除";
                    break;

                case "SET":
                    oper = "设置";
                    break;

                case "EDIT":
                case "UPDATE":
                    oper = "编辑";
                    break;
                default:
                    oper = "请求";
                    break;
            }
        }
        // 返回的值是bool类型 为false时失败
        if (object.toString().equalsIgnoreCase("false")) {
            return Result.error(ResponseEnum.SYSTEM_OPER_ERROR.newInstance(oper + "失败"));
        }
        if (object instanceof String) {
            return JSON.toJSONString(Result.success(object, oper + "成功"));
        }
        return Result.success(object, oper + "成功");
    }

    private Result defaultReturn(Class<?> returnType) {
        if (returnType != null && Collection.class.isAssignableFrom(returnType)) {
            return Result.success(new ArrayList<>(), "");
        }
        return Result.success(null, "");
    }


    //系统异常，未知异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(ResponseEnum.SYSTEM_ERROR.newInstance(e.getMessage()));
    }

    /**
     * 权限异常
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(TokenException.class)
    public Result handleException(TokenException e, HttpServletResponse response) {
        log.error("权限校验失败-》", e);
        return Result.error(ResponseEnum.AUTH_ERROR.newInstance());
    }

    //业务异常
    @ExceptionHandler(ResponseException.class)
    public Result BaseExceptionhandle(ResponseException e) {
        log.error("异常信息输出-》", e);
        return Result.error(e);
    }

}
