package com.hasaker.component.redis.service.impl;

import com.hasaker.component.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @package com.hasaker.component.redis.service.impl
 * @author 余天堂
 * @create 2020/1/3 14:59
 * @description RedisServiceImpl
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisOperations<String, Object> redisOperations;

    @Override
    public <T> void save(String key, T value, Long timeoutSecond) {
        redisOperations.opsForValue().set(key, value, timeoutSecond, TimeUnit.SECONDS);
    }

    @Override
    public <T> void save(String key, T value, Long timeout, TimeUnit timeUnit) {
        redisOperations.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        return redisOperations.hasKey(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisOperations.opsForValue().get(key);
    }
}
