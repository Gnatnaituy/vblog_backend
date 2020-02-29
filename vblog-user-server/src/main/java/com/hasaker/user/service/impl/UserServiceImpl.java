package com.hasaker.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.user.entity.User;
import com.hasaker.user.exception.enums.UserExceptionEnums;
import com.hasaker.user.mapper.UserMapper;
import com.hasaker.user.service.UserService;
import com.hasaker.vo.user.response.ResponseUserOAuthVo;
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
    public ResponseUserOAuthVo findUserByUserName(String username) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(StringUtils.isBlank(username));

        User user = userMapper.findUserByUserName(username);
        UserExceptionEnums.USER_NOT_FOUND.isTrue(ObjectUtil.isNull(user));

        ResponseUserOAuthVo userOAuthVo = new ResponseUserOAuthVo();
        userOAuthVo.setUsername(user.getUsername());
        userOAuthVo.setPassword(user.getPassword());

        return userOAuthVo;
    }
}
