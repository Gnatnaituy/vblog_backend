package com.hasaker.vblog.common;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @package com.hasaker.vblog.common
 * @author 余天堂
 * @create 2020/1/26 21:14
 * @description JwtToken
 */
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {

    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
