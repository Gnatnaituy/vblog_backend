package com.hasaker.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @package com.hasaker.common.vo
 * @author 余天堂
 * @create 2020/3/1 17:25
 * @description OAuthAccessVo
 */
@Data
public class OAuthUserVo {

    private Long id;
    private String username;
    private String nickname;
    private String avatar;

    private List<String> roles;

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String scope;
    private Long expiresIn;
    private Long expiresTime;
}
