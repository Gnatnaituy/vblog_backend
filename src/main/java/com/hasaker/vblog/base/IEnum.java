package com.hasaker.vblog.base;
/**
 * @author 余天堂
 * @since 2019/11/17 20:07
 * @description 
 */
public interface IEnum<E extends Enum<?>> {

    public String getCode();

    public String getInfo();

    public boolean equalsStr(String value);
}
