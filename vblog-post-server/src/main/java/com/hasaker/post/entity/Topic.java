package com.hasaker.post.entity;

import com.hasaker.user.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/11/3 18:02
 * @description 话题类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic extends BaseEntity {

    // 话题名称
    private String name;

    // 话题描述
    private String description;
}
