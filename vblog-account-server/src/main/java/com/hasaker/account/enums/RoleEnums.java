package com.hasaker.account.enums;

import com.hasaker.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.account.enums
 * @author 余天堂
 * @create 2020/3/27 21:10
 * @description RoleEnums
 */
@Getter
@AllArgsConstructor
public enum RoleEnums implements BaseEnum {

    ROLE_ADMIN("ROLE_ADMIN", "Administrator"),
    ROLE_USER("ROLE_USER", "User"),
    ROLE_GUEST("ROLE_GUEST", "Guest");

    private String code;
    private String message;
}
