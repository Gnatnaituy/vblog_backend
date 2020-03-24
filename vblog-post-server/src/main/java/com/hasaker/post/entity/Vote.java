package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 余天堂
 * @since 2019/10/31 22:52
 * @description 点赞实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Vote extends BaseEntity {

    private Long postId;

    private Long commentId;

    private Boolean isDownvote;

    public static final String POST_ID = "post_id";
    public static final String COMMENT_ID = "comment_id";
    public static final String IS_DOWNVOTE = "is_downvote";
}
