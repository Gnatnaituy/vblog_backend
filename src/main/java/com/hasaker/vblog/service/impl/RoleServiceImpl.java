package com.hasaker.vblog.service.impl;

import com.hasaker.vblog.base.impl.BaseServiceImpl;
import com.hasaker.vblog.entity.Role;
import com.hasaker.vblog.exception.enums.UserExceptionEnums;
import com.hasaker.vblog.mapper.RoleMapper;
import com.hasaker.vblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @package com.hasaker.vblog.service.impl
 * @author 余天堂
 * @create 2020/1/2 17:37
 * @description RoleServiceImpl
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过userId获取用户角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> getRolesByUserId(Long userId) {
        List<Role> roles = roleMapper.getRolesByUserId(userId);
        UserExceptionEnums.NO_ROLE_FOUND_FOR_THIS_USER.assertNotEmpty(roles);

        return roles;
    }
}
