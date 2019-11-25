package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 余天堂
 * @since 2019/11/3 18:19
 * @description 好友关系类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship extends BaseEntity {

    private static final long serialVersionUID = 201046591963360428L;

    private String userId;

    private String friendId;

    private String remark;
}
