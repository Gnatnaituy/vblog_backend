package com.hasaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @package com.hasaker.vblog
 * @author 余天堂
 * @create 2020/2/1 13:03
 * @description AccountApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
