package com.hasaker.account.mapper;

import com.hasaker.account.entity.User;
import com.hasaker.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2019/12/24 18:07
 * @description UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
