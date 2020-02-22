package com.hasaker.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @package com.hasaker.oauth.entity
 * @author 余天堂
 * @create 2020/2/22 11:07
 * @description User
 */
@Data
@AllArgsConstructor
public class WebUser implements UserDetails {

    private String username;

    private String password;

    private List<GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
