package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.user.entity
 * @author 余天堂
 * @create 2020/2/11 11:49
 * @description Role
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    private String name;

    public static final String NAME = "name";
}
