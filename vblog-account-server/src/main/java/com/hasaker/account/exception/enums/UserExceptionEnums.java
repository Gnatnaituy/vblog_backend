package com.hasaker.account.exception.enums;

import com.hasaker.common.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.user.exception.enums
 * @author 余天堂
 * @create 2020/2/22 15:45
 * @description UserExceptionEnums
 */
@Getter
@AllArgsConstructor
public enum UserExceptionEnums implements BaseExceptionAssert {

    USER_NOT_EXISTS("USER_EXCEPTION_001", "User not exists"),
    USERNAME_ALREADY_EXISTS("USER_EXCEPTION_002", "Username already exists");

    private String code;
    private String message;
}
