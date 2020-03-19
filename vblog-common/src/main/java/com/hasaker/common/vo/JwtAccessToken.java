package com.hasaker.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @package com.hasaker.common.vo
 * @author 余天堂
 * @create 2020/3/18 17:31
 * @description JwtAccessToken
 */
@Data
public class JwtAccessToken {

    private Long userId;
    private String username;
    private String nickname;

    private List<String> roles;

    private String jti;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String scope;
    private Long expiresIn;
    private Long expiresTime;
}
