package com.hasaker.vblog.exception.enums;

import com.hasaker.vblog.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 余天堂
 * @since 2019/12/10 15:42
 * @description 
 */
@Getter
@AllArgsConstructor
public enum PostExceptionEnums implements BaseExceptionAssert {

    POST_NOT_FOUND("POST_NOT_FOUND","post not found");

    private String code;
    private String message;
}
