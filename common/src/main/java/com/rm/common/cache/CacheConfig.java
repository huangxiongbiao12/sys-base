package com.rm.common.cache;

import com.rm.common.cache.impl.LocalCacheService;
import com.rm.common.cache.impl.RedisCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public CacheService localCacheService() {
        return new LocalCacheService();
    }

    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public CacheService redisCacheService() {
        return new RedisCacheService();
    }


}
