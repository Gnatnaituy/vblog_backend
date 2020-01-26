package com.hasaker.vblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @package com.hasaker.vblog
 * @author 余天堂
 * @create 2019/12/24 15:57
 * @description VBlogApplication
 */
@SpringBootApplication
public class VBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VBlogApplication.class, args);
    }
}
