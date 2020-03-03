package com.hasaker.oauth.config;

import com.hasaker.common.consts.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/2/22 11:56
 * @description TokenStoreConfig
 */
@Configuration
public class TokenStoreConfig {

    /**
     * JWT储存token
     */
    @Configuration
    @ConditionalOnProperty(prefix = "oauth2.token.store", name = "type", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenStoreConfig {

        @Bean
        @Primary
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey(Consts.JWT_ASSIGN_KEY);

            return accessTokenConverter;
        }

        @Bean
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }
    }

    /**
     * Redis储存token
     */
    @Configuration
    @ConditionalOnProperty(prefix = "oauth2.token.store", name = "type", havingValue = "jwt")
    public static class RedisTokenStoreConfig {

        @Autowired(required = false)
        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }
    }
}
