package com.hasaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @package com.hasaker.face
 * @author 余天堂
 * @create 2020/2/27 09:44
 * @description FaceApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(value = "com.hasaker.*.feign")
@EnableSwagger2
public class FaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceApplication.class, args);
    }
}
