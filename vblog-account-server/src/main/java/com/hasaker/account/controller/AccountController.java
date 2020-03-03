package com.hasaker.account.controller;

import com.hasaker.account.service.UserService;
import com.hasaker.common.consts.Ajax;
import com.hasaker.common.vo.OAuthUserVo;
import com.hasaker.vo.account.request.RequestUserUpdateVo;
import com.hasaker.vo.account.response.ResponseUserDetailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.user.controller
 * @author 余天堂
 * @create 2020/2/9 16:25
 * @description TestController
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录使用")
    @GetMapping(value = "/{username}")
    public Ajax<OAuthUserVo> findUserByUsername(@PathVariable String username) {
        return Ajax.getInstance().successT(userService.findUserByUserName(username));
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public Ajax register(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.createUser(username, password) ? Ajax.success() : Ajax.failure();
    }

    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/change-password")
    public Ajax changePassword(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.changePassword(username, password) ? Ajax.success() : Ajax.failure();
    }

    @ApiOperation(value = "更新用户资料")
    @PostMapping(value = "/update")
    public Ajax update(RequestUserUpdateVo userUpdateVo) {
        return userService.updateUser(userUpdateVo) ? Ajax.success() : Ajax.failure();
    }

    @ApiOperation(value = "用户详情")
    @GetMapping(value = "/detail/{username}")
    public Ajax<ResponseUserDetailVo> detail(@PathVariable("username") String username) {
        return Ajax.getInstance().successT(userService.userDetail(username));
    }
}
