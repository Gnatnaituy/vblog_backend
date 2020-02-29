package com.hasaker.user.feign;

import com.hasaker.common.consts.Ajax;
import com.hasaker.vo.user.response.ResponseUserOAuthVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @package com.hasaker.user.feign
 * @author 余天堂
 * @create 2020/2/22 15:29
 * @description UserClient
 */
@FeignClient(value = "vblog-user-server")
@RequestMapping(value = "/user")
public interface UserClient {

    @GetMapping("/{username}")
    @ResponseBody
    Ajax<ResponseUserOAuthVo> findUserByUsername(@PathVariable(value = "username") String username);
}
