package com.hasaker.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hasaker.account.entity.Role;
import com.hasaker.account.exception.enums.RoleExceptionEnums;
import com.hasaker.account.mapper.RoleMapper;
import com.hasaker.account.service.RoleService;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @package com.hasaker.account.service.impl
 * @author 余天堂
 * @create 2020/3/1 17:39
 * @description RoleServiceImpl
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据userId获取用户角色列表
     * @param userId
     * @return
     */
    @Override
    public List<String> getRolesByUserId(Long userId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        return roleMapper.getRolesByUserId(userId);
    }

    /**
     * Create a role
     * @param roleName
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String roleName) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(roleName);

        Role role = new Role();
        role.setName(roleName);

        this.save(role);
    }

    /**
     * Delete a role
     * @param roleName
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String roleName) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(roleName);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Role.NAME, roleName);
        Role role = this.getOne(queryWrapper);
        RoleExceptionEnums.ROLE_NOT_EXISTS.assertNotEmpty(role);

        this.removeById(role.getId());
    }

    /**
     * Obtain role id by role name
     * @param roleName
     * @return
     */
    @Override
    public Long getRoleIdByRoleName(String roleName) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(roleName);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Role.NAME, roleName);
        Role role = this.getOne(queryWrapper);
        RoleExceptionEnums.ROLE_NOT_EXISTS.assertNotEmpty(role);

        return role.getId();
    }
}
