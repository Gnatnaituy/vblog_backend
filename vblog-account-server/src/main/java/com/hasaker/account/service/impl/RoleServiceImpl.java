package com.hasaker.account.service.impl;

import com.hasaker.account.entity.Role;
import com.hasaker.account.mapper.RoleMapper;
import com.hasaker.account.service.RoleService;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
