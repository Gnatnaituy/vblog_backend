package com.hasaker.face.controller.user;

import com.hasaker.common.consts.Ajax;
import com.hasaker.user.feign.UserClient;
import com.hasaker.vo.user.response.ResponseUserOAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.face.controller
 * @author 余天堂
 * @create 2020/2/27 09:42
 * @description UserController
 */
@RestController
@RequestMapping(value = "open/user")
public class UserController {

    @Autowired
    private UserClient userClient;

    @GetMapping(value = "/{username}")
    public Ajax<ResponseUserOAuthVo> findUserByUsername(@PathVariable String username) {
        return userClient.findUserByUsername(username);
    }
}
