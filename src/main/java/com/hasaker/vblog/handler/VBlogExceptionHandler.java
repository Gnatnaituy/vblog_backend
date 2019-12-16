package com.hasaker.vblog.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 余天堂
 * @since 2019/12/10 15:18
 * @description 
 */
@RestControllerAdvice
public class VBlogExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e) {
        return e.toString();
    }
}
