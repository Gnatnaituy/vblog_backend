package com.hasaker.oauth.service.impl;

import cn.hutool.core.convert.Convert;
import com.hasaker.account.feign.AccountClient;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.oauth.entity.OAuthUserDetails;
import com.hasaker.oauth.service.OAuthUserDetailService;
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
    public OAuthUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseUserOAuthVo userOAuthVo = accountClient.findUserByUsername(username).getData();
        OAuthUserDetails oAuthUserDetails = Convert.convert(OAuthUserDetails.class, userOAuthVo);
        oAuthUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(
                String.join(",", userOAuthVo.getRoles())));

        return oAuthUserDetails;
    }
}
