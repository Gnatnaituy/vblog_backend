package com.hasaker.face.controller.user;

import com.hasaker.common.vo.Ajax;
import com.hasaker.common.vo.RedisAccessToken;
import com.hasaker.face.service.user.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.face.controller
 * @author 余天堂
 * @create 2020/2/27 09:42
 * @description UserRegLoginController
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService AuthService;

    @ApiOperation(value = "login")
    @PostMapping("/login")
    public Ajax<RedisAccessToken> login(@RequestParam("username") String username, @RequestParam("password") String password) {

        return Ajax.getInstance().successT(AuthService.login(username, password));
    }

    @ApiOperation(value = "register")
    @PostMapping("/register")
    public Ajax register(@RequestParam("username") String username, @RequestParam("password") String password) {
        AuthService.register(username, password);

        return Ajax.success();
    }

    @ApiOperation(value = "change password")
    @PostMapping("/change-password")
    public Ajax register(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword,
                         @RequestParam("password") String password) {
        AuthService.changePassword(username, oldPassword, password);

        return Ajax.success();
    }
}
