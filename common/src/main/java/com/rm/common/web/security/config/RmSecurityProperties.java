package com.rm.common.web.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "rm.security")
@Data
@Component
public class RmSecurityProperties {

    /**
     * 权限开关（默认开）
     */
    private boolean enable = true;

    /**
     * 是否允许跨域
     */
    private boolean crossOrigin = true;


    /**
     * token的有效时长 默认60分钟
     */
    private Long expireTime = 60L;

    /**
     * 有效时长单位
     */
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    /**
     * 可访问权限
     * <p>
     * key为接口path value为sign集合
     */
    private Map<String, String[]> perssions = new HashMap<>();

    /**
     * 不可访问权限
     * <p>
     * key为接口path value为sign集合
     */
    private Map<String, String[]> refusePerssions = new HashMap<>();

    /**
     * 不需要鉴权的path集合
     *
     */
    private String[] disauth = {};

}
