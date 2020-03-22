package com.hasaker.account.mapper;

import com.hasaker.account.entity.Role;
import com.hasaker.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @package com.hasaker.user.mapper
 * @author 余天堂
 * @create 2020/2/11 12:34
 * @description RoleMapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select name from role where is_deleted = 0 and id in " +
            "(select role_id from user_role where is_deleted = 0 and user_id = #{userId})")
    List<String> getRolesByUserId(@Param("userId") Long userId);
}
