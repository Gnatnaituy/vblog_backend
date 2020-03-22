package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2019/12/24 20:29
 * @description Image
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PostImage extends BaseEntity {

    @NonNull
    private Long postId;

    @NonNull
    private String url;

    @NonNull
    private Integer order;

    public static final String POST_ID = "post_id";
    public static final String URL = "url";
    public static final String ORDER = "order";
}
