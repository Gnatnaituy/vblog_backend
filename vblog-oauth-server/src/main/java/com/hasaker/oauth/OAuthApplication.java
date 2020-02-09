package com.hasaker.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @package com.hasaker.oauth
 * @author 余天堂
 * @create 2020/2/9 17:26
 * @description OauthApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }
}
