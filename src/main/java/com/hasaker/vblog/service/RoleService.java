package com.hasaker.vblog.service;

import com.hasaker.vblog.base.BaseService;
import com.hasaker.vblog.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @package com.hasaker.vblog.service
 * @author 余天堂
 * @create 2020/1/2 17:37
 * @description RoleService
 */
public interface RoleService extends BaseService<Role> {

    List<Role> getRolesByUserId(@Param("userId") Long userId);
}
