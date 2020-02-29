package com.hasaker.oauth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/2/22 11:56
 * @description TokenStoreConfig
 */
@Configuration
public class TokenStoreConfig {

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
            accessTokenConverter.setSigningKey("5523");

            return accessTokenConverter;
        }

        @Bean
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }
    }
}
