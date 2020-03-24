package com.hasaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @package com.hasaker.post
 * @author 余天堂
 * @create 2020/2/9 15:11
 * @description PostApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class PostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }
}
