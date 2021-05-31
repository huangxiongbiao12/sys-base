package com.rm.common.cache.impl;

import com.rm.common.cache.CacheService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCacheService implements CacheService {

    private static Map mapCache = new ConcurrentHashMap();

    @Override
    public void set(String key, Object value) {
        mapCache.put(key, value);
    }

    @Override
    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    @Override
    public void remove(String key) {
        mapCache.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return mapCache.containsKey(key);
    }

    @Override
    public Object get(String key) {
        return mapCache.get(key);
    }
}
