package com.hasaker.account.feign;

import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.account.vo.response.ResponseUserDetailVo;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.config.FeignExceptionConfig;
import com.hasaker.common.vo.Ajax;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.user.feign
 * @author 余天堂
 * @create 2020/2/22 15:29
 * @description UserClient
 */
@FeignClient(name = "VBLOG-ACCOUNT", url = "127.0.0.1:9001", configuration = FeignExceptionConfig.class)
@RestController
public interface AccountClient {

    @GetMapping("/account/{username}")
    Ajax<ResponseUserOAuthVo> findUserByUsername(@PathVariable("username") String username);

    @PostMapping("/account/register")
    Ajax register(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping("/account/change-password")
    Ajax changePassword(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping("/account/update")
    Ajax update(RequestUserUpdateVo userUpdateVo);

    @GetMapping("/account/detail/{username}")
    Ajax<ResponseUserDetailVo> detail(@PathVariable("username") String username);
}
