package com.hasaker.vblog.base.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.vblog.entity.Role;
import com.hasaker.vblog.entity.User;
import com.hasaker.vblog.service.RoleService;
import com.hasaker.vblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @package com.hasaker.vblog.service
 * @author 余天堂
 * @create 2020/1/2 15:56
 * @description VBlogUserDetailsService
 */
@Service
public class VBlogUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    public VBlogUserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username);
        if (ObjectUtils.isNotNull(user)) {
            List<Role> roles = roleService.getRolesByUserId(user.getId());
            user.setAuthorities(roles);
        }

        return user;
    }
}
