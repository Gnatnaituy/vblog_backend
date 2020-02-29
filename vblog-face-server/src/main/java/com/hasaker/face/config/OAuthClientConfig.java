package com.hasaker.face.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @package com.hasaker.face.config
 * @author 余天堂
 * @create 2020/2/27 09:53
 * @description OAuthClientConfig
 */
@EnableOAuth2Client
@Configuration
@EnableConfigurationProperties
public class OAuthClientConfig {

    /**
     * 配置受保护的资源信息
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    /**
     * 过滤器，存储当前请求和上下文
     * @return
     */
    @Bean
    public RequestInterceptor oAuth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate (){
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }
}
