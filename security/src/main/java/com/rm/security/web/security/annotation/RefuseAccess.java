package com.rm.security.web.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不允许identify权限访问
 * 该注解用于controller类的接口方法上
 *  拥有其中一个权限，即不能访问
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RefuseAccess {

    String[] identify() default {};

}
