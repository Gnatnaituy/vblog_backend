package com.hasaker.vblog.enums;
/**
 * @author 余天堂
 * @since 2019/11/17 20:08
 * @description 
 */
public enum YNEnums implements IEnum<YNEnums> {

    YES("1", "Y"),
    NO("0", "N");

    private final String code;
    private final String info;

    YNEnums(String code, String info) {
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
