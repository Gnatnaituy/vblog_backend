package com.hasaker.vblog.controller;

import com.hasaker.vblog.common.AjaxResult;
import com.hasaker.vblog.exception.enums.CommonExceptionEnums;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.vblog.controller
 * @author 余天堂
 * @create 2020/1/6 15:27
 * @description TestController
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/exception")
    public AjaxResult testException() {
        throw CommonExceptionEnums.NOT_NULL_ARG.newException("测试Exception!");

    }
}
