package com.hasaker.face.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @package com.hasaker.oauth.config.jwt
 * @author 余天堂
 * @create 2020/3/15 22:02
 * @description JwtAccessTokenConverter
 */
@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    /**
     * Set Authentication details with access token claims
     * DefaultAccessTokenConverter used to set Authentication details to Null.
     * @param claims
     * @return
     */
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);

        return authentication;
    }
}
