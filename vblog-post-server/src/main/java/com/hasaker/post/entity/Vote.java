package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/10/31 22:52
 * @description 点赞实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Vote extends BaseEntity {

    @NonNull
    private Long postId;

    private Long commentId;
}
