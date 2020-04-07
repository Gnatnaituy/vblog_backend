package com.hasaker.face.controller.user;

import com.hasaker.common.vo.Ajax;
import com.hasaker.common.vo.RedisAccessToken;
import com.hasaker.face.service.user.AuthService;
import com.hasaker.face.vo.request.RequestChangePasswordVo;
import com.hasaker.face.vo.request.RequestLoginVo;
import com.hasaker.face.vo.request.RequestRegisterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Ajax<RedisAccessToken> login(@RequestBody RequestLoginVo loginVo) {

        return Ajax.getInstance().successT(AuthService.login(loginVo.getUsername(), loginVo.getPassword()));
    }

    @ApiOperation(value = "register")
    @PostMapping("/register")
    public Ajax register(@RequestBody RequestRegisterVo registerVo) {
        AuthService.register(registerVo.getUsername(), registerVo.getPassword());

        return Ajax.success();
    }

    @ApiOperation(value = "change password")
    @PostMapping("/change-password")
    public Ajax register(@RequestBody RequestChangePasswordVo changePasswordVo) {
        AuthService.changePassword(changePasswordVo.getUsername(), changePasswordVo.getOldPassword(), changePasswordVo.getNewPassword());

        return Ajax.success();
    }
}
