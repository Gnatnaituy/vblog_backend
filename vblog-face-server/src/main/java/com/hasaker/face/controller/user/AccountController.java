package com.hasaker.face.controller.user;

import com.hasaker.account.feign.AccountClient;
import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.common.vo.Ajax;
import com.hasaker.face.controller.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "Update user's detail information by username")
    @PostMapping(value = "/update")
    public Ajax update(@RequestBody RequestUserUpdateVo userUpdateVo) {
        userUpdateVo.setUserId(getUserIdLong());
        return accountClient.update(userUpdateVo);
    }
}
