package com.hasaker.vblog.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2019/12/22 00:52
 * @description Status
 */
@Getter
@AllArgsConstructor
public enum Status {

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(500, "INTERNAL SERVER ERROR");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 内容
     */
    private String message;
}
