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
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    // 动态内容
    private String content;

    // 动态可见性
    private Integer visibility;

    // 地址ID
    private Long locationId;
}
