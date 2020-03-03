package com.hasaker.oauth.service.impl;

import cn.hutool.core.convert.Convert;
import com.hasaker.account.feign.AccountClient;
import com.hasaker.common.vo.OAuthUserVo;
import com.hasaker.oauth.entity.OAuthUser;
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
    public OAuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuthUserVo oAuthUserVo = accountClient.findUserByUsername(username).getData();
        OAuthUser oAuthUser = Convert.convert(OAuthUser.class, oAuthUserVo);
        oAuthUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(
                String.join(",", oAuthUserVo.getRoles())));

        return oAuthUser;
    }
}
