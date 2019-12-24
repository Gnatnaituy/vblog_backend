package com.hasaker.vblog.exception.enums;

import com.hasaker.vblog.exception.BaseExceptionAssert;

/**
 * @author 余天堂
 * @since 2019/12/10 15:42
 * @description 
 */
public enum PostExceptionEnums implements BaseExceptionAssert {

    POST_NOT_FOUND("POST_NOT_FOUND","post not found");

    private String code;

    private String message;

    PostExceptionEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
