package com.hasaker.post.entity;

import com.hasaker.user.base.BaseEntity;
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
@AllArgsConstructor
@Builder
public class Image extends BaseEntity {

    // 拥有者ID
    private Long ownerId;

    // 拥有者类型
    private Long ownerType;

    // 图片大小
    private Float size;

    // 是否为默认图片
    private Boolean isDefault;

    // 顺序
    private Integer order;
}
