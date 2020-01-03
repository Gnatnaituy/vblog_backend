package com.hasaker.vblog.component.redis.service;

/**
 * @package com.hasaker.vblog.component.redis.service
 * @author 余天堂
 * @create 2020/1/3 14:59
 * @description RedisService
 */
public interface RedisService {

    boolean hasKey(String key);

    Object get(String key);
}
