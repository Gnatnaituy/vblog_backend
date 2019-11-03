package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 余天堂
 * @since 2019/11/3 18:08
 * @description 
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImage extends BaseEntity {

    private String postId;

    private String imageUrl;

    private Integer imageSize;
}
