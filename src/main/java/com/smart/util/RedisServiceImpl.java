package com.smart.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 *
 * @author LiaoQinZhou
 * @date: 2021/2/4 16:14
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${redis.defaultTTL}")
    private Integer defaultTTL;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        if (this.exists(key)) {
            Duration duration = Duration.ofMinutes(defaultTTL);
            //this.expire(key, duration.getSeconds());
        }
    }

    @Override
    public String get(String key) {
        if (exists(key)) {
            Duration duration = Duration.ofMinutes(defaultTTL);
            //this.expire(key, duration.getSeconds());
            return stringRedisTemplate.opsForValue().get(key);
        }
        return null;
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }


    @Override
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public boolean isExpire(String key) {
        return expire(key) > 1 ? false : true;
    }


    @Override
    public long expire(String key) {
        return stringRedisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
