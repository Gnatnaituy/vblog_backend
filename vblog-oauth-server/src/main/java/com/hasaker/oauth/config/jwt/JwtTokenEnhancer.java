package com.hasaker.oauth.config.jwt;

import com.hasaker.oauth.entity.OAuthUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/3/15 21:47
 * @description JwtTokenEnhancer
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        if (accessToken instanceof DefaultOAuth2AccessToken) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuthUserDetails) {
                Map<String, Object> enhancedInfo = new HashMap<>();
                enhancedInfo.put("userId", ((OAuthUserDetails) principal).getUserId());
                enhancedInfo.put("username", ((OAuthUserDetails) principal).getUsername());
                enhancedInfo.put("password", ((OAuthUserDetails) principal).getPassword());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(enhancedInfo);
            }
        }

        return accessToken;
    }
}
