package com.hasaker.vblog.config.shiro;

import com.hasaker.vblog.common.JwtToken;
import com.hasaker.vblog.component.redis.service.RedisService;
import com.hasaker.vblog.service.UserService;
import com.hasaker.vblog.utils.JWTUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.vblog.config
 * @author 余天堂
 * @create 2020/1/26 21:09
 * @description 自定义权限控制类
 */
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证 登录
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token = principalCollection.getPrimaryPrincipal().toString();
        String username = JWTUtils.getUsername(token);

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
