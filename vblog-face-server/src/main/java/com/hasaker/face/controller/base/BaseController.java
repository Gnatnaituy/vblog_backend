package com.hasaker.face.controller.base;

import com.hasaker.common.consts.RequestConsts;
import com.hasaker.common.vo.RedisAccessToken;
import com.hasaker.component.redis.service.RedisService;
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
    @Autowired
    private RedisService redisService;

    /**
     * 获取当前用户的ID
     * @return
     */
    public String getUserIdString() {
        String token = request.getHeader(RequestConsts.AUTHORIZATION).split(" ")[1];
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

        return accessToken.getAdditionalInformation().get("userId").toString();
    }

    /**
     * 获取当前用户的ID
     * @return
     */
    public Long getUserIdLong() {
        String token = request.getHeader(RequestConsts.AUTHORIZATION).split(" ")[1];
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

        return Long.valueOf(accessToken.getAdditionalInformation().get("userId").toString());
    }

    /**
     * 获取当前用户的账户
     * @return
     */
    public String getUsername() {
        String token = request.getHeader(RequestConsts.AUTHORIZATION).split(" ")[1];
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

        return accessToken.getAdditionalInformation().get("user_name").toString();
    }

    /**
     * 获取当前用户的详尽信息
     * @return
     */
    public RedisAccessToken getAccessToken() {
        String username = getUsername();

        return redisService.get(username, RedisAccessToken.class);
    }
}
