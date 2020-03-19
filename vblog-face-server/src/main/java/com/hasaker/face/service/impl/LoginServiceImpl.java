package com.hasaker.face.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.hasaker.account.feign.AccountClient;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.consts.RequestConsts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.JwtAccessToken;
import com.hasaker.common.vo.RedisAccessToken;
import com.hasaker.component.redis.service.RedisService;
import com.hasaker.face.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

/**
 * @package com.hasaker.face.service.impl
 * @author 余天堂
 * @create 2020/3/4 11:22
 * @description LoginServiceImpl
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestOperations restOperations;
    @Autowired
    private RedisService redisService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public JwtAccessToken login(String username, String password) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(username);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(password);

        ResponseUserOAuthVo userOAuthVo = accountClient.findUserByUsername(username).getData();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", userOAuthVo.getUsername());
        params.add("password", password);
        params.add("grant_type", "password");
        params.add("scope", "all");
        params.add("client_id", "face-client");
        params.add("client_secret", "5523");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restOperations
                .exchange(RequestConsts.URL_OAUTH_TOKEN, HttpMethod.POST, requestEntity, String.class);
        JwtAccessToken jwtAccessToken = JSONObject.parseObject(responseEntity.getBody(), JwtAccessToken.class);
        jwtAccessToken.setExpiresTime(System.currentTimeMillis() + jwtAccessToken.getExpiresIn());

        // 将当期登录用户信息放入redis
        RedisAccessToken redisAccessToken = Convert.convert(RedisAccessToken.class, jwtAccessToken);
        redisService.save(username, redisAccessToken);

        return jwtAccessToken;
    }

    /**
     * 注册
     * @param username
     * @param password
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String username, String password) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(username);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(password);

        password = passwordEncoder.encode(password);
        accountClient.register(username, password);
    }
}
