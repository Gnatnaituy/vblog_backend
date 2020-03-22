package com.hasaker.account.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @package com.hasaker.vo.user.response
 * @author 余天堂
 * @create 2020/2/22 15:32
 * @description ResponseUserOauthVo
 */
@Data
public class ResponseUserOAuthVo {

    private Long userId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private List<String> roles;
}
