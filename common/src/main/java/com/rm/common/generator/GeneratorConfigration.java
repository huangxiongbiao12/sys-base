package com.rm.common.generator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class GeneratorConfigration implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GeneratorConfigration.applicationContext = applicationContext;
    }

    public static GeneratorProperties properties() {
        GeneratorProperties properties = applicationContext.getBean(GeneratorProperties.class);
        // 公共数据源配置
        if (StringUtils.isEmpty(properties.getUrl())) {
            GeneratorConfigration configration = applicationContext.getBean(GeneratorConfigration.class);
            properties.setDriverClassName(configration.driverClassName);
            properties.setUrl(configration.url);
            properties.setUsername(configration.username);
            properties.setPassword(configration.password);
        }
        return properties;
    }

}
