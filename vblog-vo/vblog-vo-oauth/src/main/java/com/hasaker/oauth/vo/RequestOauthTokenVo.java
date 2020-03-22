package com.hasaker.oauth.vo;

import lombok.Data;

/**
 * @package com.hasaker.oauth.vo
 * @author 余天堂
 * @create 2020/3/18 17:37
 * @description RequestOauthTokenVo
 */
@Data
public class RequestOauthTokenVo {

    private String username;

    private String password;

    private String grantType;

    private String scope;
}
