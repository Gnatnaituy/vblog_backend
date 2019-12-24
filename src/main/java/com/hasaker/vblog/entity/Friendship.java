package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
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

    // 用户ID
    private Long userId;

    // 好友ID
    private Long friendId;

    // 好友备注
    private String remark;

    // 好友权限
    private String visibility;
}
