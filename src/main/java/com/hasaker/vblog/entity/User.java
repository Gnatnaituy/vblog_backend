package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import com.hasaker.vblog.enums.UserStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author 余天堂
 * @since 2019/10/31 22:43
 * @description 用户实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    // 用户账号/用户名
    private String username;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 性别
    private String gender;

    // 年龄
    private Integer age;

    // 签名
    private String bio;

    // 国家
    private String country;

    // 省
    private String province;

    // 市
    private String city;

    // 用户状态
    private String status;

    // 用户角色
    private List<Role> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserStatusEnums.ENABLED.equalsStr(status);
    }
}
