package com.hasaker.account.exception.enums;

import com.hasaker.common.exception.base.IExceptionAssert;
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
public enum UserExceptionEnums implements IExceptionAssert {

    USER_NOT_FOUND("USER_EXCEPTION_001", "User not found");

    private String code;
    private String message;
}
