package com.hasaker.face.service;

import com.hasaker.common.vo.JwtAccessToken;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/4 11:22
 * @description LoginService
 */
public interface LoginService {

    JwtAccessToken login(String username, String password);

    void register(String username, String password);
}
