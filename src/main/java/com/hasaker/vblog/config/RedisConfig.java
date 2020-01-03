package com.hasaker.vblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @package com.hasaker.vblog.config
 * @author 余天堂
 * @create 2020/1/3 09:34
 * @description RedisConfig
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration()
                .master("master")
                .sentinel("127.0.0.1", 7003)
                .sentinel("127.0.0.1", 7004)
                .sentinel("127.0.0.1", 7005);

        return new LettuceConnectionFactory(sentinelConfiguration);
    }
}
