package com.hasaker.account.service;

import com.hasaker.account.entity.User;
import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.base.BaseService;

/**
 * @package com.hasaker.vblog.service
 * @author 余天堂
 * @create 2020/1/2 17:19
 * @description UserService
 */
public interface UserService extends BaseService<User> {

    ResponseUserOAuthVo findUserByUserName(String username);

    void createUser(String username, String password);

    void changePassword(String username, String password);

    void updateUser(RequestUserUpdateVo userUpdateVo);

    void indexAll();
}
