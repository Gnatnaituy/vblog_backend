package com.hasaker.account.enums;

import com.hasaker.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.account.enums
 * @author 余天堂
 * @create 2020/3/19 23:27
 * @description FriendRequestStatusEnum
 */
@Getter
@AllArgsConstructor
public enum FriendRequestStatusEnum implements BaseEnum {

    NOT_READ("FRIEND_REQUEST_STATUS_001", "Not read by target user yet"),
    ACCEPTED("FRIEND_REQUEST_STATUS_002", "Accepted by target user"),
    DENIED("FRIEND_REQUEST_STATUS_003", "Denied by target user"),
    IGNORED("FRIEND_REQUEST_STATUS_004", "Ignored by target user");

    private String code;
    private String message;
}
