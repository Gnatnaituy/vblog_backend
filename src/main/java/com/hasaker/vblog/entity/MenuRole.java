package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2020/1/2 17:57
 * @description MenuRole
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRole extends BaseEntity {

    private Long menuId;

    private Long roleId;
}
