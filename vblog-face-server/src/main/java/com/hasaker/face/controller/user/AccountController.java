package com.hasaker.face.controller.user;

import com.hasaker.account.feign.AccountClient;
import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.common.vo.Ajax;
import com.hasaker.face.controller.base.BaseController;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.face.controller.user
 * @author 余天堂
 * @create 2020/3/26 15:01
 * @description AccountController
 */
@RestController
@RequestMapping(value = "/user/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Update user's detail information by username")
    @PostMapping(value = "/update")
    public Ajax update(@RequestBody RequestUserUpdateVo userUpdateVo) {
        userUpdateVo.setUserId(getUserId());
        return accountClient.update(userUpdateVo);
    }

    @ApiOperation(value = "Obtain current user's detail information")
    @GetMapping(value = "/info")
    public Ajax<ResponseUserDetailVo> info() {
        return Ajax.getInstance().successT(userService.detail(getUserId(), getUserId()));
    }
}
