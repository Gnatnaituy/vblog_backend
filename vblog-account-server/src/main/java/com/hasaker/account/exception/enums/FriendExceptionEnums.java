package com.hasaker.account.exception.enums;

import com.hasaker.common.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.account.exception.enums
 * @author 余天堂
 * @create 2020/3/2 11:46
 * @description FriendshipExceptionEnums
 */
@Getter
@AllArgsConstructor
public enum FriendExceptionEnums implements BaseExceptionAssert {

    FRIEND_NOT_EXISTS("FRIEND_EXCEPTION_001", "Friend not exists");

    private String code;
    private String message;
}
