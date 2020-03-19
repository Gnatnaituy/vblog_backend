package com.hasaker.component.redis.service;

/**
 * @package com.hasaker.component.redis.service
 * @author 余天堂
 * @create 2020/1/3 14:59
 * @description RedisService
 */
public interface RedisService {

    boolean hasKey(String key);

    <T> void save(String key, T value);

    <T> T get(String key, Class<T> clazz);
}
