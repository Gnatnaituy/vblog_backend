package com.hasaker.account.enums;

import com.hasaker.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.account.enums
 * @author 余天堂
 * @create 2020/3/2 11:28
 * @description VisibilityEnums
 */
@Getter
@AllArgsConstructor
public enum VisibilityEnums implements BaseEnum {

    VISIBLE_FOR_BOTH("VISIBILITY_001", "Visible for both side"),
    NOT_VISIBLE_FOR_FRIEND("VISIBILITY_002", "Don't let friend see my posts"),
    NOT_VISIBLE_FOR_ME("VISIBILITY_003", "Don't see friend's posts"),
    NOT_VISIBLE_FOR_BOTH("VISIBILITY_004", "Don't see each other");

    private String code;
    private String message;
}
