package com.hasaker.vblog.enums;

import com.hasaker.vblog.base.IEnum;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2020/1/2 10:42
 * @description ImageOwnerTypeEnums
 */
public enum ImageOwnerTypeEnums implements IEnum<ImageOwnerTypeEnums> {

    POST("IMAGE_OWNER_TYPE_001", "Post's pictures"),
    USER_AVATAR("IMAGE_OWNER_TYPE_002", "User's avatar"),
    USER_BACKGROUND("IMAGE_OWNER_TYPE_003", "User's background"),
    TOPIC_BRAND("IMAGE_OWNER_TYPE_004", "Topic's brand"),
    TOPIC_BACKGROUND("IMAGE_OWNER_TYPE_005", "Topic's background");

    private final String code;
    private final String info;

    ImageOwnerTypeEnums(String code, String info) {
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
