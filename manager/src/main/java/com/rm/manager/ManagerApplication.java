package com.rm.manager;

import com.rm.common.generator.annotation.EnableCodeGenerate;
import com.rm.common.web.annotation.EnableWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCodeGenerate
@EnableWebConfig
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

}