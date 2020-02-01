package com.hasaker.vblog.controller;

import com.hasaker.vblog.common.AjaxResult;
import com.hasaker.vblog.exception.enums.CommonExceptionEnums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.vblog.controller
 * @author 余天堂
 * @create 2020/1/6 15:27
 * @description TestController
 */
@Api(value = "测试Controller")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/exception")
    @ApiOperation(value = "测试异常")
    public AjaxResult testException() {
        throw CommonExceptionEnums.NOT_NULL_ARG.newException("测试Exception!");

    }
}
