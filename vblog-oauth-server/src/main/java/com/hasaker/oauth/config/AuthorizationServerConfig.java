package com.hasaker.oauth.config;

import com.hasaker.oauth.config.jwt.JwtTokenEnhancer;
import com.hasaker.oauth.service.OAuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * @package com.hasaker.oauth.config
 * @author 余天堂
 * @create 2020/2/11 12:45
 * @description AuthorizationServerConfiguration
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OAuthUserDetailService userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private JwtTokenEnhancer tokenEnhancer;

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter));

        endpoints.userDetailsService(userDetailService) // 读取验证用户的信息
                .authenticationManager(authenticationManager) // 开启密码验证，来源于 WebSecurityConfigurerAdapter
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService)
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("face-client")
                .secret(passwordEncoder.encode("5523"))
                .scopes("all")
                .autoApprove(true)
                .accessTokenValiditySeconds(3600)       // 1 hour
                .refreshTokenValiditySeconds(2592000)   // 30 days
                .authorizedGrantTypes("client_credentials", "authorization_code", "password", "refresh_token")
                .redirectUris("http://localhost:9005/login");
    }

    /**
     * 允许表单认证
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * /oauth/authorize(授权端，授权码模式使用)
     * /oauth/token(令牌端，获取 token)
     * /oauth/check_token(资源服务器用来校验token)
     * /oauth/confirm_access(用户发送确认授权)
     * /oauth/error(认证失败)
     * /oauth/token_key(如果使用JWT，可以获的公钥用于 token 的验签)
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401
        security.allowFormAuthenticationForClients();
        // 资源服务器用来校验token
        security.checkTokenAccess("isAuthenticated()");
        // 如果使用JWT且非对称加密，可以获的公钥用于token的验签, 默认enyAll(), 这里用permitAll()开启
        security.tokenKeyAccess("permitAll()");
    }
}
