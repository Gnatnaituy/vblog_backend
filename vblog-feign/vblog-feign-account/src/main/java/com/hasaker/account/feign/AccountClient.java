package com.hasaker.account.feign;

import com.hasaker.common.consts.Ajax;
import com.hasaker.common.vo.OAuthUserVo;
import com.hasaker.vo.account.request.RequestUserUpdateVo;
import com.hasaker.vo.account.response.ResponseUserDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.user.feign
 * @author 余天堂
 * @create 2020/2/22 15:29
 * @description UserClient
 */
@FeignClient(value = "vblog-account-server")
@RestController
public interface AccountClient {

    @GetMapping("/account/{username}")
    Ajax<OAuthUserVo> findUserByUsername(@PathVariable("username") String username);

    @PostMapping("/account/register")
    Ajax register(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping("/account/change-password")
    Ajax changePassword(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping("/account/update")
    Ajax update(RequestUserUpdateVo userUpdateVo);

    @GetMapping("/account/detail/{username}")
    Ajax<ResponseUserDetailVo> detail(@PathVariable("username") String username);
}
