package com.hasaker.account.service.impl;

import com.hasaker.account.entity.UserRole;
import com.hasaker.account.mapper.UserRoleMapper;
import com.hasaker.account.service.UserRoleService;
import com.hasaker.common.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.account.service.impl
 * @author 余天堂
 * @create 2020/3/26 14:13
 * @description UserRoleServiceImpl
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
