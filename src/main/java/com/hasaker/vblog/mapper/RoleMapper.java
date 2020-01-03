package com.hasaker.vblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.vblog.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2020/1/2 16:03
 * @description RoleMapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT role.id, role.roleName " +
            "FROM role LEFT JOIN user_role ON role.id = user_role.role_id " +
            "WHERE user_role.user_id = ${userId}" )
    List<Role> getRolesByUserId(@Param("userId") Long userId);
}
