package com.hasaker.oauth.authentication.provider;

import cn.hutool.core.util.ObjectUtil;
import com.hasaker.oauth.entity.WebUser;
import com.hasaker.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @package com.hasaker.oauth.authentication.provider
 * @author 余天堂
 * @create 2020/2/22 12:26
 * @description WebLoginAuthenticationProvider
 */
@Configuration
public class WebLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        Object password = authentication.getCredentials();
        this.verificationUser(principal, password);

        WebUser webUser = userService.loadUserByUsername(principal.toString());
        if (ObjectUtil.isNull(webUser)) {
            throw new BadCredentialsException("User not exists!");
        }
        if (!passwordEncoder.matches(password.toString(), webUser.getPassword())) {
            throw new BadCredentialsException("Wrong password!");
        }

        return new UsernamePasswordAuthenticationToken(principal, password, webUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 检查用户名密码是否为空
     * spring oauth内部组织了自己的异常封装，认证失败必须抛出AuthenticationException类型的异常,，此处不能使用我们业务异常枚举
     * http.formLogin().loginPage(OAUTH_LOGIN)
     * 			.loginProcessingUrl(permitUrlProperties.getLoginProcessUrl())
     * 			.successHandler(authenticationSuccessHandler)
     * 		     .failureUrl(OAUTH_LOGIN);
     * 		     failureUrl方法才能调转到错误页面
     * @param username
     * @param password
     */
    private void verificationUser(Object username,Object password) {
        if (ObjectUtil.isEmpty(username)) {
            throw new BadCredentialsException("the account cannot be empty");
        } else if (ObjectUtil.isEmpty(password)) {
            throw new BadCredentialsException("the password cannot be empty");
        }
    }
}
