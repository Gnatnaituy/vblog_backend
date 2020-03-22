package com.hasaker.post.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/11/3 18:02
 * @description 话题类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Topic extends BaseEntity {

    @NonNull
    private String name;

    private String description;
}
