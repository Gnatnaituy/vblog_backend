package com.hasaker.vblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @package com.hasaker.vblog
 * @author 余天堂
 * @create 2019/12/24 15:57
 * @description VBlogApplication
 */
@MapperScan("com.hasaker.vblog.mapper")
@SpringBootApplication
public class VBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VBlogApplication.class, args);
    }
}
