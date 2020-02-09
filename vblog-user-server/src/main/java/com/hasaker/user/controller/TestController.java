package com.hasaker.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.user.controller
 * @author 余天堂
 * @create 2020/2/9 16:25
 * @description TestController
 */
@RestController
@RequestMapping(value = "/user")
public class TestController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @GetMapping(value = "/test")
    public String getDbUrl() {
        return dbUrl;
    }
}
