package com.hasaker.post.enums;

import com.hasaker.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.post.enums
 * @author 余天堂
 * @create 2020/3/22 20:18
 * @description VisibilityEnum
 */
@Getter
@AllArgsConstructor
public enum VisibilityEnum implements BaseEnum {

    PUBLIC("POST_VISIBILITY_001", "Public"),
    FRIEND("POST_VISIBILITY_002", "Visible for friends"),
    PRIVATE("POST_VISIBILITY_003", "Visible for creator only");

    private String code;
    private String message;
}
