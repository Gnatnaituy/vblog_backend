package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2019/12/24 20:29
 * @description Image
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PostImage extends BaseEntity {

    private Long postId;

    private String url;

    private Integer sort;

    public static final String POST_ID = "post_id";
    public static final String URL = "url";
    public static final String SORT = "sort";
}
