package com.hasaker.oauth.service.impl;

import com.hasaker.account.feign.AccountClient;
import com.hasaker.oauth.entity.OAuthUser;
import com.hasaker.oauth.service.OAuthUserDetailService;
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
    private AccountClient accountClient;

    @Override
    public OAuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseUserOAuthVo userOAuthVo = accountClient.findUserByUsername(username).getData();
        OAuthUser OAuthUser = new OAuthUser();
        OAuthUser.setId(userOAuthVo.getId());
        OAuthUser.setUsername(userOAuthVo.getUsername());
        OAuthUser.setPassword(userOAuthVo.getPassword());
        OAuthUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("CUSTOMER"));

        return OAuthUser;
    }
}
