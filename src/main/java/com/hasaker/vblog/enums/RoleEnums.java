package com.hasaker.vblog.enums;

import com.hasaker.vblog.base.IEnum;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2020/1/2 20:22
 * @description RoleEnums
 */
public enum RoleEnums implements IEnum<RoleEnums> {

    ADMIN("ADMIN", "Administrator"),
    USER("USER", "User"),
    GUEST("GUEST", "Guest");

    private final String code;
    private final String info;

    RoleEnums(String code, String info) {
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
