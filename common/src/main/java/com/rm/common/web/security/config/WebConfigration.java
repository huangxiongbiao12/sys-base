package com.rm.common.web.security.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.rm.common.web.security.interceptor.AuthInterceptor;
import com.rm.common.web.security.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Administrator on 2017/8/16.
 * WebMvc配置
 */
@Configuration
public class WebConfigration extends WebMvcConfigurerAdapter {

    /**
     *  返回LocalDateTime值（去掉LocalDateTime中的T）
     */
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

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

    /**
     * 接收前端datetime参数
     * @return
     */
    @Bean
    public Converter<String, LocalDateTime> LocalDateTimeConvert() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
                LocalDateTime date = null;
                try {
                    date = LocalDateTime.parse((String) source,df);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }



}
