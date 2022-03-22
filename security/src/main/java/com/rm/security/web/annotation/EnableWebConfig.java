package com.rm.security.web.annotation;

import com.rm.security.repeat.filter.RepeatableFilter;
import com.rm.security.repeat.interceptor.impl.SameUrlDataInterceptor;
import com.rm.security.web.response.ResponseHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入mvc结果处理器 权限拦截器等
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ResponseHandler.class, RepeatableFilter.class, SameUrlDataInterceptor.class})
public @interface EnableWebConfig {
}
