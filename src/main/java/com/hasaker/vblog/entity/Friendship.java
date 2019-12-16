package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/11/3 18:19
 * @description 好友关系类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship extends BaseEntity {

    private static final long serialVersionUID = 201046591963360428L;

    private String userId;

    private String friendId;

    private String remark;
}
