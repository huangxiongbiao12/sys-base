package com.rm.common.web.security.annotation;

import com.rm.common.web.security.config.EPlatform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于 Controller 上 区分客户端接口检验 token web端的接口需要web端的token才能访问
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPlatform {

    EPlatform value() default EPlatform.Any;

}
