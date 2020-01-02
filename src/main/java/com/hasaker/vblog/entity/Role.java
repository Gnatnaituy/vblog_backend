package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @package com.hasaker.vblog.entity
 * @author 余天堂
 * @create 2020/1/2 15:58
 * @description Role
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {

    // 角色名称
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
