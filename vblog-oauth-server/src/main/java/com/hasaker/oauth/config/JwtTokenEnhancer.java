package com.hasaker.oauth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/2/18 14:13
 * @description JwtTokenEnhancer
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> enhancedInfo = new HashMap<>();
        enhancedInfo.put("enhanced", "Enhanced Info");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(enhancedInfo);

        return accessToken;
    }
}
