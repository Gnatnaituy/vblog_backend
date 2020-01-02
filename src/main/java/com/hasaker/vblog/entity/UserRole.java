package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2020/1/2 16:32
 * @description UserRole
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseEntity {

    // 用户ID
    private Long userId;

    // 角色ID
    private Long roleId;
}
