package com.hasaker.account.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hasaker.account.entity.User;
import com.hasaker.account.exception.enums.UserExceptionEnums;
import com.hasaker.account.mapper.UserMapper;
import com.hasaker.account.service.UserService;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
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
        userOAuthVo.setId(user.getId());
        userOAuthVo.setUsername(user.getUsername());
        userOAuthVo.setPassword(user.getPassword());

        return userOAuthVo;
    }
}
