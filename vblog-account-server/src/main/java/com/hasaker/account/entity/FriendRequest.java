package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @package com.hasaker.account.entity
 * @author 余天堂
 * @create 2020/3/19 22:32
 * @description FriendRequest
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class FriendRequest extends BaseEntity {

    @NonNull
    private Long senderId;

    private String senderUsername;

    private String senderNickname;

    private String senderAvatar;

    private String senderRemark;

    private String senderVisibility;

    @NonNull
    private Long receiverId;

    private String receiverUsername;

    private String receiverNickname;

    private String receiverAvatar;

    private String requestStatus;

    public static final String SENDER_ID = "sender_id";
    public static final String SENDER_USERNAME = "sender_username";
    public static final String SENDER_NICKNAME = "sender_nickname";
    public static final String SENDER_AVATAR = "sender_avatar";
    public static final String SENDER_REMARK = "sender_remark";
    public static final String SENDER_VISIBILITY = "sender_visibility";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String RECEIVER_USERNAME = "receiver_username";
    public static final String RECEIVER_NICKNAME = "receiver_nickname";
    public static final String RECEIVER_AVATAR = "receiver_avatar";
    public static final String REQUEST_STATUS = "request_status";
}
