package com.hasaker.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.account.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.user.mapper
 * @author 余天堂
 * @create 2020/2/11 12:34
 * @description RoleMapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
