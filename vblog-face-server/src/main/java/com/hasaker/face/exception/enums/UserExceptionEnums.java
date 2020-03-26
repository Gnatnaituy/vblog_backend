package com.hasaker.face.exception.enums;

import com.hasaker.common.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.face.exception.enums
 * @author 余天堂
 * @create 2020/3/26 14:26
 * @description UserExceptionEnums
 */
@Getter
@AllArgsConstructor
public enum UserExceptionEnums implements BaseExceptionAssert {

    USER_NOT_EXISTS("USER_EXCEPTION_001", "User not exists"),
    INCORRECT_OLD_PASSWORD("USER_EXCEPTION_002", "Incorrect old password");

    private String code;
    private String message;
}
