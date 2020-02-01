package com.hasaker.vblog.component.redis.service;

/**
 * @package com.hasaker.vblog.component.redis.service
 * @author 余天堂
 * @create 2020/1/30 11:11
 * @description DistributedLockService
 */
public interface DistributedLockService {

    boolean tryLock(String lockKey, String value, long expire);

    boolean releaseLock(String lockKey, String value);
}
