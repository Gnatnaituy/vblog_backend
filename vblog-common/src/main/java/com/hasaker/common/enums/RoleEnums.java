package com.hasaker.common.enums;

import com.hasaker.common.base.IEnum;
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
public enum RoleEnums implements IEnum<RoleEnums> {

    ADMIN("ADMIN", "Administrator"),
    USER("USER", "User"),
    GUEST("GUEST", "Guest");

    private final String code;
    private final String info;
}
