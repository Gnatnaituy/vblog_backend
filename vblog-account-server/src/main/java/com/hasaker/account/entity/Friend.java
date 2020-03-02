package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/11/3 18:19
 * @description 好友类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend extends BaseEntity {

    // 用户ID
    private Long userId;

    // 好友ID
    private Long friendId;

    // 好友备注
    private String remark;

    // 好友权限
    private String visibility;

    public static final String USER_ID = "user_id";
    public static final String FRIEND_ID = "friend_id";
    public static final String REMARK = "remark";
    public static final String VISIBILITY = "visibility";
}
