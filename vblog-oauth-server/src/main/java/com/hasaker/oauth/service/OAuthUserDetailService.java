package com.hasaker.oauth.service;

import com.hasaker.oauth.entity.OAuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/2/18 14:53
 * @description VBlogUserDetailService
 */
public interface OAuthUserDetailService extends UserDetailsService {

    OAuthUser loadUserByUsername(String username);
}
