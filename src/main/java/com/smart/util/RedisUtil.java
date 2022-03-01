package com.smart.util;

import java.util.List;
import java.util.Set;

/**
 * @author smart
 * @descrption 操作redis五种类型数据
 * string set zset list hash
 * @date 2022年2月8日10:02:40
 */
public abstract class RedisUtil {

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    abstract boolean set(String key, Object value);

    /**
     * 保存数据并设置过期时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    abstract boolean set(String key, Object value, Long expireTime);

    /**
     * 批量删除key
     *
     * @param keys
     */
    abstract void remove(String... keys);

    /**
     * 批量删除key
     *
     * @param pattern
     */
    abstract void removePattern(String pattern);

    /**
     * 删除对应的value
     *
     * @param key
     */
    abstract void remove(String key);


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    abstract boolean exists(String key);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    abstract Object get(String key);


    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    abstract void hmSet(String key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    abstract Object hmGet(String key, Object hashKey);


    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    abstract void lPush(String k, Object v);


    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    abstract List<Object> lRange(String k, long l, long l1);


    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    abstract void setArray(String key, Object value);


    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    abstract Set<Object> getArray(String key);

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    abstract void zAdd(String key, Object value, double scoure);

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    abstract Set<Object> rangeByScore(String key, double scoure, double scoure1);

}
