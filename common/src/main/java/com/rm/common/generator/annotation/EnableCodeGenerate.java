package com.rm.common.generator.annotation;

import com.rm.common.generator.GeneratorConfigration;
import com.rm.common.generator.GeneratorProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GeneratorConfigration.class, GeneratorProperties.class})
public @interface EnableCodeGenerate {

}
