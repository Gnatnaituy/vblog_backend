package com.hasaker.user.service;

import com.hasaker.common.base.BaseService;
import com.hasaker.user.entity.User;
import com.hasaker.vo.user.response.ResponseUserOAuthVo;

/**
 * @package com.hasaker.vblog.service
 * @author 余天堂
 * @create 2020/1/2 17:19
 * @description UserService
 */
public interface UserService extends BaseService<User> {

    ResponseUserOAuthVo findUserByUserName(String username);
}
