package com.hasaker.component.redis.service.impl;

import com.hasaker.component.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

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
    public boolean hasKey(String key) {
        return redisOperations.hasKey(key);
    }

    @Override
    public Object get(String key) {
        return redisOperations.opsForValue().get(key);
    }
}
