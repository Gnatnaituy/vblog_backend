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

    private static final long serialVersionUID = 5213807671160609429L;

    private Long postId;

    private Long targetUserId;

    private Long commentUserId;

    private String content;

    private Integer visibility;

    private Long commentTime;
}
