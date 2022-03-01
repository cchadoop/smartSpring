package com.smart.util;

/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 判断key是否过期
     *
     * @param key
     * @return
     */
    boolean isExpire(String key);

    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     *
     * @param key
     * @return
     */
    long expire(String key);

}
