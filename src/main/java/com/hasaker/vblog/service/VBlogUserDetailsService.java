package com.hasaker.vblog.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.vblog.entity.Role;
import com.hasaker.vblog.entity.User;
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

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


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
