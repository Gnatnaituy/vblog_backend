package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/10/31 17:00
 * @description 动态实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Post extends BaseEntity {

    @NonNull
    private String content;

    @NonNull
    private Integer visibility;

    public static final String CONTENT = "content";
    public static final String VISIBILITY = "visibility";
}
