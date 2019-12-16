package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/11/3 18:08
 * @description 动态图片类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImage extends BaseEntity {

    private static final long serialVersionUID = -4575068402038558681L;

    private String postId;

    private String imageUrl;

    private Integer imageSize;
}
