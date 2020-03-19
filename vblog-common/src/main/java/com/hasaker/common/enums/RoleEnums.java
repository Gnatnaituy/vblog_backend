package com.hasaker.common.enums;

import com.hasaker.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2020/1/2 20:22
 * @description RoleEnums
 */
@Getter
@AllArgsConstructor
public enum RoleEnums implements BaseEnum<RoleEnums> {

    ADMIN("ADMIN", "Administrator"),
    USER("USER", "User"),
    GUEST("GUEST", "Guest");

    private final String code;
    private final String message;
}
