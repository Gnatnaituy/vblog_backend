package com.hasaker.account.service;

import com.hasaker.account.entity.User;
import com.hasaker.common.base.BaseService;
import com.hasaker.common.vo.OAuthUserVo;
import com.hasaker.vo.account.request.RequestUserUpdateVo;
import com.hasaker.vo.account.response.ResponseUserDetailVo;

/**
 * @package com.hasaker.vblog.service
 * @author 余天堂
 * @create 2020/1/2 17:19
 * @description UserService
 */
public interface UserService extends BaseService<User> {

    OAuthUserVo findUserByUserName(String username);

    boolean createUser(String username, String password);

    boolean changePassword(String username, String password);

    boolean updateUser(RequestUserUpdateVo userUpdateVo);

    ResponseUserDetailVo userDetail(String username);
}
