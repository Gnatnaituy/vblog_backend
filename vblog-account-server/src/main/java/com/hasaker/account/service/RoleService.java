package com.hasaker.account.service;

import com.hasaker.account.entity.Role;
import com.hasaker.common.base.BaseService;

import java.util.List;

/**
 * @package com.hasaker.account.service
 * @author 余天堂
 * @create 2020/3/1 17:39
 * @description RoleService
 */
public interface RoleService extends BaseService<Role> {

    List<String> getRolesByUserId(Long userId);
}
