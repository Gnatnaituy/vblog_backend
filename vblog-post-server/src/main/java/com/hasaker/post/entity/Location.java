package com.hasaker.post.entity;

import com.hasaker.user.base.BaseEntity;
import lombok.*;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2019/12/24 20:46
 * @description Location
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends BaseEntity {

    // 动态ID
    private Long postId;

    // 经度
    private Double longitude;

    // 维度
    private Double latitude;
}
