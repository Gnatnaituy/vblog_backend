package com.hasaker.face.controller.user;

import com.hasaker.common.vo.Ajax;
import com.hasaker.common.vo.JwtAccessToken;
import com.hasaker.face.service.LoginService;
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
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Ajax<JwtAccessToken> login(@RequestParam("username") String username, @RequestParam("password") String password) {

        return Ajax.getInstance().successT(loginService.login(username, password));
    }

    @PostMapping("/register")
    public Ajax register(@RequestParam("username") String username, @RequestParam("password") String password) {
        loginService.register(username, password);

        return Ajax.success();
    }
}
