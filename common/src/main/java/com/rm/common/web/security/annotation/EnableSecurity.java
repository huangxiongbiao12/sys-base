package com.rm.common.web.security.annotation;

import com.rm.common.web.security.config.RmSecurityProperties;
import com.rm.common.web.security.config.WebConfigration;
import com.rm.common.web.security.interceptor.AuthInterceptor;
import com.rm.common.web.security.token.TokenManager;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RmSecurityProperties.class, WebConfigration.class, AuthInterceptor.class, TokenManager.class})
public @interface EnableSecurity {
}
