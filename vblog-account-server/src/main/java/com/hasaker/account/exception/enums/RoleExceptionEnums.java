package com.hasaker.account.exception.enums;

import com.hasaker.common.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.account.exception.enums
 * @author 余天堂
 * @create 2020/3/26 13:31
 * @description RoleExceptionEnums
 */
@Getter
@AllArgsConstructor
public enum RoleExceptionEnums implements BaseExceptionAssert {

    ROLE_NOT_EXISTS("ROLE_EXCEPTION_001", "Role not exists");

    private String code;
    private String message;
}
