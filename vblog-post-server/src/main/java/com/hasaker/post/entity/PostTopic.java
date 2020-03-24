package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2019/12/24 20:27
 * @description PostTopic
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PostTopic extends BaseEntity {

    @NonNull
    private Long postId;

    @NonNull
    private Long topicId;

    public static final String POST_ID = "post_id";
    public static final String TOPIC_ID = "topic_id";
}
