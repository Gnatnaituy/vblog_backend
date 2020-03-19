package com.hasaker.oauth.controller;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @package com.hasaker.oauth.controller
 * @author 余天堂
 * @create 2020/3/15 23:13
 * @description TokenController
 */
@RestController
public class TokenController {

    @Resource(name = "tokenServices")
    private ConsumerTokenServices tokenServices;
}
