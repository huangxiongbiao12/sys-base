package com.rm.common.generator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfigration implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GeneratorConfigration.applicationContext = applicationContext;
    }

    public static GeneratorProperties properties() {
        return applicationContext.getBean(GeneratorProperties.class);
    }

}
