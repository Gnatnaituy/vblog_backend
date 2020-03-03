package com.hasaker.common.exception.enums;

import com.hasaker.common.consts.MessageConsts;
import com.hasaker.common.exception.base.IExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 余天堂
 * @since 2019/11/17 21:05
 * @description
 */
@Getter
@AllArgsConstructor
public enum CommonExceptionEnums implements IExceptionAssert {

    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!"),

    NOT_NULL("101", MessageConsts.EMPTY_OBJECT),
    NOT_NULL_ARG("102", MessageConsts.EMPTY_ARG),
    OPERATION_FAILED("103", MessageConsts.FAILED);

    private String code;
    private String message;
}
