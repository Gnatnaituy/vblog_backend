package com.hasaker.vblog.exception.enums;

import com.hasaker.vblog.common.MessageConsts;
import com.hasaker.vblog.exception.base.BaseExceptionAssert;

/**
 * @author 余天堂
 * @since 2019/11/17 21:05
 * @description 
 */
public enum CommonExceptionEnums implements BaseExceptionAssert {

    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!"),

    NOT_NULL("100", MessageConsts.ENTITY_EMPTY),
    NOT_NULL_ARG("101", MessageConsts.ARG_ENTITY_EMPTY),
    ERROR_SNOWFLAKE_SIZE("102", MessageConsts.ERROR_SNOWFLAKE_SIZE),
    OPERATION_FAILED("103", MessageConsts.OPERATION_FAILED);

    private String code;

    private String message;

    CommonExceptionEnums(String code, String message) {
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
