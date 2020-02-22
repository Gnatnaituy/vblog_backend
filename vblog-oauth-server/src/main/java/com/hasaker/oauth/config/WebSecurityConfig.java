package com.hasaker.oauth.config;

import com.hasaker.oauth.authentication.provider.WebLoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/2/11 11:35
 * @description AuthorizationServerConfiguration
 */
@EnableWebSecurity // 开启Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级别上的保护
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WebLoginAuthenticationProvider webLoginAuthenticationProvider;

    /**
     * 密码加密工具类，它可以实现不可逆的加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 实现 OAuth2 的 password 模式必须要指定的授权管理 Bean
     * 注意 是重写authenticationManagerBean() 而不是重写authenticationManager()
     *     否则会抛出StackOverflowError !!!
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(webLoginAuthenticationProvider);
    }

    /**
     * 允许匿名访问所有接口 主要是 oauth 接口
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/login/**", "/logout/**", "/open/**")
                .permitAll()
                .and()
                .csrf().disable();
    }
}
