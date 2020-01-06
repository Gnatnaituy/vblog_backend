package com.hasaker.vblog.exception.enums;

import com.hasaker.vblog.exception.base.BaseExceptionAssert;

/**
 * @author 余天堂
 * @since 2019/12/10 15:42
 * @description 
 */
public enum UserExceptionEnums implements BaseExceptionAssert {

    USER_NOT_EXISTS("USER_EXCEPTION_001","user not exists"),
    NO_ROLE_FOUND_FOR_THIS_USER("USER_EXCEPTION_002", "no.role.found.for.this.user");

    private String code;

    private String message;

    UserExceptionEnums(String code, String message) {
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
