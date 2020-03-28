package com.hasaker.face.service.user;

import com.hasaker.common.vo.RedisAccessToken;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/4 11:22
 * @description LoginService
 */
public interface AuthService {

    RedisAccessToken login(String username, String password);

    void register(String username, String password);

    void changePassword(String username, String oldPassword, String password);
}
