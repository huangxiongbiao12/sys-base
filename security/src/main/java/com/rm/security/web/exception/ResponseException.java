package com.rm.security.web.exception;

import com.rm.security.web.response.ResponseEnum;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class ResponseException extends RuntimeException {

    private int code;

    public ResponseException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMsg());
    }

    public ResponseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
