package com.hasaker.account.feign;

import com.hasaker.common.consts.Ajax;
import com.hasaker.common.vo.OAuthUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @package com.hasaker.user.feign
 * @author 余天堂
 * @create 2020/2/22 15:29
 * @description UserClient
 */
@FeignClient(value = "vblog-account-server")
public interface AccountClient {

    @GetMapping("/account/{username}")
    @ResponseBody
    Ajax<OAuthUserVo> findUserByUsername(@PathVariable(value = "username") String username);
}
