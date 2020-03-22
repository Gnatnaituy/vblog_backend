package com.hasaker.common.enums;

import com.hasaker.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.vblog.enums
 * @author 余天堂
 * @create 2020/1/2 10:42
 * @description ImageOwnerTypeEnums
 */
@Getter
@AllArgsConstructor
public enum ImageOwnerTypeEnums implements BaseEnum {

    POST("IMAGE_OWNER_TYPE_001", "Post's pictures"),
    USER_AVATAR("IMAGE_OWNER_TYPE_002", "User's avatar"),
    USER_BACKGROUND("IMAGE_OWNER_TYPE_003", "User's background"),
    TOPIC_BRAND("IMAGE_OWNER_TYPE_004", "Topic's brand"),
    TOPIC_BACKGROUND("IMAGE_OWNER_TYPE_005", "Topic's background");

    private final String code;
    private final String message;
}
