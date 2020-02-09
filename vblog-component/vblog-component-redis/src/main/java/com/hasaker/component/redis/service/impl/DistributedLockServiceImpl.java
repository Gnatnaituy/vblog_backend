package com.hasaker.component.redis.service.impl;

import com.hasaker.component.redis.service.DistributedLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.types.Expiration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @package com.hasaker.component.redis.service.impl
 * @author 余天堂
 * @create 2020/1/30 11:12
 * @description DistributedLockServiceImpl
 */
public class DistributedLockServiceImpl implements DistributedLockService {

    @Autowired
    private RedisOperations<String, Object> redisOperations;

    public static final String UNLOCK_LUA =
            "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
            "then " +
            "    return redis.call(\"del\",KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end ";

    /**
     * 获取锁
     * @param lockKey
     * @param value
     * @param expire
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, String value, long expire) {
        RedisCallback<Boolean> callback = (connection) ->
                connection.set(lockKey.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8),
                Expiration.milliseconds(expire), RedisStringCommands.SetOption.SET_IF_ABSENT);

        return redisOperations.execute(callback);
    }

    /**
     * 释放锁
     * @param lockKey
     * @param value
     * @return
     */
    @Override
    public boolean releaseLock(String lockKey, String value) {
        List<String> keys = Collections.singletonList(lockKey);
        List<String> values = Collections.singletonList(value);

        Long result = redisOperations.execute((RedisCallback<Long>) redisConnection -> {
            Object nativeConnection = redisConnection.getNativeConnection();
            // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, values);
            } else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, values);
            }

            return 0L;
        });

        return result != null && result > 0L;
    }
}
