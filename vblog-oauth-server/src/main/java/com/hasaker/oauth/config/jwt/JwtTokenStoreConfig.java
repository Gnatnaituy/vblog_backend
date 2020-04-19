package com.hasaker.oauth.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/3/15 21:49
 * @description JwtTokenStoreConfig
 */
@Configuration
public class JwtTokenStoreConfig {

    @Value("${security.oauth2.private-key}")
    private String PRIVATE_KEY;
    @Value("${security.oauth2.public-key}")
    private String PUBLIC_KEY;

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);

        return defaultTokenServices;
    }

    @Bean
    @Primary
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 设置非对称加密方式
     * @return
     */
    @Bean
    @Primary
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(PRIVATE_KEY);
        accessTokenConverter.setVerifierKey(PUBLIC_KEY);

        return accessTokenConverter;
    }
}
