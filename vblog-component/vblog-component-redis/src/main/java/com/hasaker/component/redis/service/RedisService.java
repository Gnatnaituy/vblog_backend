package com.hasaker.component.redis.service;

import java.util.concurrent.TimeUnit;

/**
 * @package com.hasaker.component.redis.service
 * @author 余天堂
 * @create 2020/1/3 14:59
 * @description RedisService
 */
public interface RedisService {

    <T> void save(String key, T value, Long timeoutSecond);

    <T> void save(String key, T value, Long timeout, TimeUnit timeUnit);

    boolean hasKey(String key);

    <T> T get(String key, Class<T> clazz);
}
