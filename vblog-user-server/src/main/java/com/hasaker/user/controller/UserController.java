package com.hasaker.user.controller;

import com.hasaker.common.consts.Ajax;
import com.hasaker.user.service.UserService;
import com.hasaker.vo.user.response.ResponseUserOAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.user.controller
 * @author 余天堂
 * @create 2020/2/9 16:25
 * @description TestController
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{username}")
    public Ajax<ResponseUserOAuthVo> findUserByUsername(@PathVariable String username) {
        return Ajax.getInstance().successT(userService.findUserByUserName(username));
    }
}
