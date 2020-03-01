package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/11/3 18:19
 * @description 好友关系类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship extends BaseEntity {

    // 好友ID
    private Long friendId;

    // 好友备注
    private String remark;

    // 好友权限
    private String visibility;
}
