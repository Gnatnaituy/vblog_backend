package com.hasaker.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hasaker.user.base.impl.BaseServiceImpl;
import com.hasaker.user.entity.User;
import com.hasaker.user.exception.enums.CommonExceptionEnums;
import com.hasaker.user.mapper.UserMapper;
import com.hasaker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.vblog.service.impl
 * @author 余天堂
 * @create 2020/1/2 17:21
 * @description UserServiceImpl
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserName(String username) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(StringUtils.isBlank(username));

        return userMapper.findUserByUserName(username);
    }
}
