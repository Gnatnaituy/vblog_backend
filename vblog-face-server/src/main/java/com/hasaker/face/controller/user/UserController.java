package com.hasaker.face.controller.user;

import com.hasaker.account.feign.AccountClient;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.vo.Ajax;
import com.hasaker.face.controller.base.BaseController;
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
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private AccountClient accountClient;

    @GetMapping(value = "/{username}")
    public Ajax<ResponseUserOAuthVo> findUserByUsername(@PathVariable String username) {
        return accountClient.findUserByUsername(username);
    }

    @GetMapping(value = "/info")
    public Ajax<Object> getUserAccessToken() {
        return Ajax.getInstance().successT(getUserId());
    }
}
