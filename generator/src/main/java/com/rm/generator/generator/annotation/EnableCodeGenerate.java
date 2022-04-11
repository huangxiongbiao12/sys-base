package com.rm.generator.generator.annotation;

import com.rm.generator.generator.GeneratorConfigration;
import com.rm.generator.generator.GeneratorProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GeneratorConfigration.class, GeneratorProperties.class})
public @interface EnableCodeGenerate {

}
