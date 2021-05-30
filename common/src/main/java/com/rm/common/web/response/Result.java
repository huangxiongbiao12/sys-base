package com.rm.common.web.response;

import com.rm.common.web.exception.ResponseException;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求成功响应类
 */
@Data
public class Result implements Serializable {
    /**
     * 返回状态码 默认0是成功
     */
    private int code = 0;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * true接口成功，false接口失败
     */
    public Boolean success;

    /**
     * 请求成功或失败的提示信息
     */
    public String message;

    public static Result success(Object data) {
        return success(data, "请求成功");
    }

    public static Result success(Object data, String message) {
        Result br = new Result();
        br.setData(data);
        br.setSuccess(true);
        br.setMessage(message);
        return br;
    }

    public static Result error(ResponseException exception) {
        Result br = new Result();
        br.setSuccess(false);
        br.setMessage(exception.getMessage());
        br.setCode(exception.getCode());
        return br;
    }

    public static Result error(int code, String msg){
        Result br = new Result();
        br.setSuccess(false);
        br.setMessage(msg);
        br.setCode(code);
        return br;
    }
}
