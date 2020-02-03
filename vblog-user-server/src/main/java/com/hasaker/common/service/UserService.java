package com.hasaker.common.service;

import com.hasaker.common.base.BaseService;
import com.hasaker.common.entity.User;

/**
 * @package com.hasaker.vblog.service
 * @author 余天堂
 * @create 2020/1/2 17:19
 * @description UserService
 */
public interface UserService extends BaseService<User> {

    User findUserByUserName(String username);
}
