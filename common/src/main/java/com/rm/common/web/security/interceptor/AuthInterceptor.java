package com.rm.common.web.security.interceptor;

import com.rm.common.utils.CollectionUtils;
import com.rm.common.web.security.annotation.CheckPlatform;
import com.rm.common.web.security.annotation.Disauth;
import com.rm.common.web.security.config.RmSecurityProperties;
import com.rm.common.web.security.token.Token;
import com.rm.common.web.security.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * Created by Administrator on 2017/8/14.
 * 登陆鉴权拦截器
 */
@Component
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private RmSecurityProperties rmSecurityProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Exposed-Headers", Token.HEADER_TOKEN);
            Method method = ((HandlerMethod) handler).getMethod();
            log.info(method.getName());
            TokenManager.set(null);
            if ("error".equals(method.getName())) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return true;
            } else if ("errorHtml".equals(method.getName())) {
                return true;
            }
            //解析token登陆状态
            String path = request.getContextPath();
            //如果标注了Disauth或者配置文件disauth中存在path，则直接返回成功
            if (method.getDeclaringClass().getAnnotation(Disauth.class) != null
                    || method.getAnnotation(Disauth.class) != null
                    || checkDisauth(path)) {
                return true;
            }
            Token token = tokenManager.parse(request.getHeader(Token.HEADER_TOKEN));
            // 区分平台检验token
            if (method.getDeclaringClass().getAnnotation(CheckPlatform.class) != null
                    || method.getAnnotation(CheckPlatform.class) != null) {
                CheckPlatform checkPlatform = method.getDeclaringClass().getAnnotation(CheckPlatform.class);
                if (checkPlatform == null) {
                    checkPlatform = method.getAnnotation(CheckPlatform.class);
                }
                if (!checkPlatform.value().equals(token.getPlatform())) {
                    //登陆验证失败，标注401
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
            //验证token登陆状态
            token = tokenManager.check(token);
            // 验证token有效，且包含该接口权限则通过
            if (token != null && token.confirm(path, method, rmSecurityProperties)) {
                return true;
            } else {
                //登陆验证失败，标注401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            //因为return的是false，所以直接记录日志
        } else if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        return false;
    }

    /**
     * 正则匹配
     *
     * @return
     */
    private boolean checkDisauth(String path) {
        String[] disauths = rmSecurityProperties.getDisauth();
        if (!CollectionUtils.isEmpty(disauths)) {
            for (String authPattern : disauths) {
                if (path.matches(authPattern)) {
                    return true;
                }
            }
        }
        return false;
    }

}
