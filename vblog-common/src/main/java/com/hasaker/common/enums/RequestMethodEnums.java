package com.hasaker.common.enums;

import com.hasaker.common.base.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 余天堂
 * @since 2019/11/17 20:10
 * @description
 */
@Getter
@AllArgsConstructor
public enum RequestMethodEnums implements IEnum<RequestMethodEnums> {

    GET("GET", "GET"),
    POST("POST", "POST"),
    UPDATE("UPDATE", "UPDATE"),
    DELETE("DELETE", "DELETE");

    private final String code;
    private final String message;
}
