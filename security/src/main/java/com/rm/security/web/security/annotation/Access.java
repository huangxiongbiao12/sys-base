package com.rm.security.web.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于controller类的接口方法上
 * identify为该接口访问权限，没有此权限不能访问该接口
 * 拥有其中一个权限，即可访问
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {

    String[] identify() default {};

}
