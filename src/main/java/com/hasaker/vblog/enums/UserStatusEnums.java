package com.hasaker.vblog.enums;

import com.hasaker.vblog.base.IEnum;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2020/1/2 16:59
 * @description UserStatusEnums
 */
public enum UserStatusEnums implements IEnum<UserStatusEnums> {

    ENABLED("ENABLED", "User enabled"),
    DISABLED("DISABLED", "User disabled");

    private final String code;
    private final String info;

    UserStatusEnums(String code, String info) {
        this.code = code;
        this.info = info;
    }

    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String getInfo()
    {
        return info;
    }

    @Override
    public boolean equalsStr(String value) {
        return this.getCode().equals(value);
    }
}
