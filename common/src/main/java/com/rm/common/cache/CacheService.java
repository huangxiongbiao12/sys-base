package com.rm.common.cache;


public interface CacheService {

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    void set(final String key, Object value);


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
