package com.hasaker.component.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @package com.hasaker.vblog.config
 * @author 余天堂
 * @create 2020/1/3 09:34
 * @description RedisConfig
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;
    @Autowired
    private RedisClusterConfiguration redisClusterConfiguration;
    @Autowired
    private GenericObjectPoolConfig JedisPoolConfig;

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisOperations<String, Object> redisOperations(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public RedisClusterConfiguration getJedisCuster() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        List<String> clusterNodes = redisProperties.getCluster().getNodes();
        List<RedisNode> redisNodes = new ArrayList<>(clusterNodes.size());

        for (String clusterNode : clusterNodes) {
            String[] addressPort = clusterNode.split(":");
            redisNodes.add(new RedisNode(addressPort[0], Integer.parseInt(addressPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(redisNodes);

        return redisClusterConfiguration;
    }

    @Bean
    public GenericObjectPoolConfig getJedisPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        genericObjectPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().getSeconds() * 1000);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        genericObjectPoolConfig.setTestOnBorrow(true);

        return genericObjectPoolConfig;
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder jedisClientConfigurationBuilder
                = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.connectTimeout(Duration.ofMillis(5000));
        jedisClientConfigurationBuilder.poolConfig(JedisPoolConfig);
        jedisClientConfigurationBuilder.readTimeout(redisProperties.getTimeout());

        JedisConnectionFactory jedisConnectionFactory =
                new JedisConnectionFactory(redisClusterConfiguration, jedisClientConfigurationBuilder.build());
        // 连接池初始化
        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;
    }
}
