package com.rm.common.web.file.annotation;

import com.rm.common.web.file.FileController;
import com.rm.common.web.file.service.impl.FileServiceImpl;
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
@Import({FileController.class, FileServiceImpl.class})
public @interface EnableFileUpload {
}
