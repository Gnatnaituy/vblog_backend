package com.hasaker.face.controller.base;

import com.hasaker.common.consts.RequestConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;

/**
 * @package com.hasaker.face.controller.base
 * @author 余天堂
 * @create 2020/3/1 09:00
 * @description BaseController
 */
public class BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenStore tokenStore;

    public Long getUserId() {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(request
                .getHeader(RequestConsts.AUTHORIZATION).split(" ")[1]);
        return Long.valueOf(accessToken.getAdditionalInformation().get("userId").toString());
    }

    public String getUsername() {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(request
                .getHeader(RequestConsts.AUTHORIZATION).split(" ")[1]);
        return accessToken.getAdditionalInformation().get("user_name").toString();
    }
}
