package com.hasaker.face.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.hasaker.account.feign.AccountClient;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.consts.RequestConsts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.RedisAccessToken;
import com.hasaker.component.redis.service.RedisService;
import com.hasaker.face.exception.enums.UserExceptionEnums;
import com.hasaker.face.service.user.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/4 11:22
 * @description LoginServiceImpl
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestOperations restOperations;
    @Autowired
    private RedisService redisService;

    /**
     * Login
     * @param username
     * @param password
     * @return
     */
    @Override
    public RedisAccessToken login(String username, String password) {
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
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = restOperations
                .exchange(RequestConsts.URL_OAUTH_TOKEN, HttpMethod.POST, requestEntity, String.class);

        RedisAccessToken redisAccessToken = JSONObject.parseObject(responseEntity.getBody(), RedisAccessToken.class);
        assert redisAccessToken != null;
        redisAccessToken.setExpiresTime(System.currentTimeMillis() + redisAccessToken.getExpiresIn() * 1000);
        redisAccessToken.setNickname(userOAuthVo.getNickname());
        redisAccessToken.setAvatar(userOAuthVo.getAvatar());
        redisAccessToken.setBackground(userOAuthVo.getBackground());
        redisAccessToken.setBio(userOAuthVo.getBio());
        redisAccessToken.setRoles(userOAuthVo.getRoles());

        // Save the current user's information to redis
        redisService.save(username, redisAccessToken, redisAccessToken.getExpiresIn() );

        return redisAccessToken;
    }

    /**
     * Register
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

    /**
     * Change password
     * @param username
     * @param password
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String username, String oldPassword, String password) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(username);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(oldPassword);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(password);

        ResponseUserOAuthVo userOAuthVo = accountClient.findUserByUsername(username).getData();
        UserExceptionEnums.INCORRECT_OLD_PASSWORD.isFalse(passwordEncoder.matches(oldPassword, userOAuthVo.getPassword()));

        accountClient.changePassword(username, passwordEncoder.encode(password));
    }
}
