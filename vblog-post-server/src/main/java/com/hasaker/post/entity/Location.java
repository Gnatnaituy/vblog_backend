package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
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
@RequiredArgsConstructor
public class Location extends BaseEntity {

    @NonNull
    private Long postId;

    @NonNull
    private Double longitude;

    @NonNull
    private Double latitude;

    @NonNull
    private String description;
}
