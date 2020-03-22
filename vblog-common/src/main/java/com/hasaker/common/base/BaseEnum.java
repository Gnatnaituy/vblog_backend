package com.hasaker.common.base;
/**
 * @author 余天堂
 * @since 2019/11/17 20:07
 * @description
 */
public interface BaseEnum {

    String getCode();

    String getMessage();

    default boolean equalStr(String value) {
        return this.getCode().equals(value);
    }
}
