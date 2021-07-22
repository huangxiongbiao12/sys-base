package com.rm.common.web.security.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/8/14.
 * 标明相应方法不需要鉴权
 * <br/>只针对controller中的方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Disauth {

}
