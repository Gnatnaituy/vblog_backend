package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @package com.hasaker.post.entity
 * @author 余天堂
 * @create 2020/3/22 18:44
 * @description DownVote
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DownVote extends BaseEntity {

    @NonNull
    private Long postId;

    private Long commentId;
}
