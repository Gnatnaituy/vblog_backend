package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2020/1/2 17:57
 * @description Menu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {

    // 后端url
    private String url;

    // 前端path
    private String path;

    // vue组件名称
    private String component;

    // 名称
    private String name;

    // 父级MenuId
    private Long parentId;

    // 子集Menu
    private List<Menu> children;

    // 访问所需权限
    private List<Role> roles;

    // 是否启用
    private Boolean enabled;
}
