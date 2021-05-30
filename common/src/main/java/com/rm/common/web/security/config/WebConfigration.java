package com.rm.common.web.security.config;

import com.rm.common.web.security.interceptor.AuthInterceptor;
import com.rm.common.web.security.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/8/16.
 * WebMvc配置
 */
@Configuration
public class WebConfigration extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private RmSecurityProperties rmSecurityProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (rmSecurityProperties.isEnable()) {
            registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        }
        super.addInterceptors(registry);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (rmSecurityProperties.isCrossOrigin()) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
//                    .allowCredentials(true)
                    .allowedMethods("GET", "POST", "OPTIONS")
                    .allowedHeaders("*")
                    .exposedHeaders(Token.HEADER_TOKEN)
                    .maxAge(3600);
        }
    }


}
