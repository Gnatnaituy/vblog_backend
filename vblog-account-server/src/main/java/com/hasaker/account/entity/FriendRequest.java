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

    private String senderRemark;

    private Integer senderVisibility;

    @NonNull
    private Long receiverId;

    private String requestStatus;

    public static final String SENDER_ID = "sender_id";
    public static final String SENDER_REMARK = "sender_remark";
    public static final String SENDER_VISIBILITY = "sender_visibility";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String REQUEST_STATUS = "request_status";
}
