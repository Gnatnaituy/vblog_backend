package com.hasaker.oauth.service.impl;

import com.hasaker.oauth.entity.WebUser;
import com.hasaker.oauth.service.OAuthUserDetailService;
import com.hasaker.user.feign.UserClient;
import com.hasaker.vo.user.response.ResponseUserOAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.oauth.service.impl
 * @author 余天堂
 * @create 2020/2/22 11:20
 * @description UserServiceImpl
 */
@Service
public class OAuthUserDetailServiceImpl implements OAuthUserDetailService {

    @Autowired
    private UserClient userClient;

    @Override
    public WebUser loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseUserOAuthVo userOAuthVo = userClient.findUserByUsername(username).getData();
        WebUser webUser = new WebUser();
        webUser.setUsername(userOAuthVo.getUsername());
        webUser.setPassword(userOAuthVo.getPassword());
        webUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));

        return webUser;
    }
}
