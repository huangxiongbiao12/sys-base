package com.rm.common.cache;


import java.util.concurrent.TimeUnit;

public interface CacheService {

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, Object value);

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, Object value, Long expireTime);


    boolean set(final String key, Object value, Long expireTime, TimeUnit unit);

    /**
     * 延长key的有效时间
     *
     * @param key
     * @param expireTime
     * @param unit
     * @return
     */
    boolean expire(String key, Long expireTime, TimeUnit unit);


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    void remove(final String... keys);

    /**
     * 删除对应的value
     *
     * @param key
     */
    void remove(final String key);

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    boolean contains(final String key);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    Object get(final String key);

}
