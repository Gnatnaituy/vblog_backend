package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/10/31 22:48
 * @description 评论实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    // 评论动态ID
    private Long postId;

    // 回复对象用户ID
    private Long targetUserId;

    // 评论用户ID
    private Long commentUserId;

    // 评论内容
    private String content;

    // 可见性
    private Integer visibility;
}
