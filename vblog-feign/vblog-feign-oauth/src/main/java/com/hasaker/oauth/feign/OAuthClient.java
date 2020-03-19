package com.hasaker.oauth.feign;

import com.hasaker.common.interceptor.FeignRequestInterceptor;
import com.hasaker.common.vo.JwtAccessToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.oauth.feign
 * @author 余天堂
 * @create 2020/3/18 17:28
 * @description OAuthClient
 */
@FeignClient(name = "VBLOG-OAUTH", url = "127.0.0.1:9003", configuration = FeignRequestInterceptor.class)
@RestController
public interface OAuthClient {

    @PostMapping("/oauth/token")
    JwtAccessToken login(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("grant_Type") String grantType,
                         @RequestParam("scope") String scope);
}
