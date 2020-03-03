package com.hasaker.vo.account.response;

import lombok.Data;

/**
 * @package com.hasaker.vo.user.response
 * @author 余天堂
 * @create 2020/2/22 15:32
 * @description ResponseUserOauthVo
 */
@Data
public class ResponseUserOAuthVo {

    private Long id;

    private String username;

    private String password;
}
