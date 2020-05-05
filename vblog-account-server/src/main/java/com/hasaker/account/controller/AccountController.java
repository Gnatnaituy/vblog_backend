package com.hasaker.account.controller;

import com.hasaker.account.service.FriendRequestService;
import com.hasaker.account.service.FriendService;
import com.hasaker.account.service.UserService;
import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.vo.Ajax;
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
    @Autowired
    private FriendService friendService;
    @Autowired
    private FriendRequestService friendRequestService;

    @ApiOperation(value = "Login")
    @GetMapping(value = "/{username}")
    public Ajax<ResponseUserOAuthVo> findUserByUsername(@PathVariable String username) {

        return Ajax.getInstance().successT(userService.findUserByUserName(username));
    }

    @ApiOperation(value = "Register")
    @PostMapping(value = "/register")
    public Ajax register(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.createUser(username, password);

        return Ajax.success();
    }

    @ApiOperation(value = "Change password")
    @PostMapping(value = "/change-password")
    public Ajax changePassword(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.changePassword(username, password);

        return Ajax.success();
    }

    @ApiOperation(value = "Update user's detail information by username")
    @PostMapping(value = "/update")
    public Ajax update(@RequestBody RequestUserUpdateVo userUpdateVo) {
        userService.updateUser(userUpdateVo);

        return Ajax.success();
    }

    @ApiOperation(value = "Index all user information to ES")
    @GetMapping(value = "/index-all")
    public Ajax indexAll() {
        userService.indexAll();
        friendService.indexAll();
        friendRequestService.indexAll();

        return Ajax.success();
    }
}
