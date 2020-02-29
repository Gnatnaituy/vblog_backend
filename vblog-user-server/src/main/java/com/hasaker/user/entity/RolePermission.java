package com.hasaker.user.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.user.entity
 * @author 余天堂
 * @create 2020/2/11 11:49
 * @description RolePermission
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseEntity {

    private Long roleId;

    private Long permissionId;
}
