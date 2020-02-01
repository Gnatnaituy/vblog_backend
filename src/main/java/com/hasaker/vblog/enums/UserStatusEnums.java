package com.hasaker.vblog.enums;

import com.hasaker.vblog.base.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2020/1/2 16:59
 * @description UserStatusEnums
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnums implements IEnum<UserStatusEnums> {

    ENABLED("ENABLED", "User enabled"),
    DISABLED("DISABLED", "User disabled");

    private final String code;
    private final String info;
}
