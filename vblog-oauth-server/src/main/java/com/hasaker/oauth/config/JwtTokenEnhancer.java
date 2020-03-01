package com.hasaker.oauth.config;

import com.hasaker.oauth.entity.OAuthUser;
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

        if (accessToken instanceof DefaultOAuth2AccessToken) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuthUser) {
                Map<String, Object> enhancedInfo = new HashMap<>();
                enhancedInfo.put("id", ((OAuthUser) principal).getId());
                enhancedInfo.put("username", ((OAuthUser) principal).getUsername());
                enhancedInfo.put("password", ((OAuthUser) principal).getPassword());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(enhancedInfo);
            }
        }

        return accessToken;
    }
}
