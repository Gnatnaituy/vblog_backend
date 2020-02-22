package com.hasaker.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.user.controller
 * @author 余天堂
 * @create 2020/2/9 16:25
 * @description TestController
 */
@RestController
@RequestMapping(value = "/user")
public class TestController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @GetMapping(value = "/test")
    public String getDbUrl() {
        return dbUrl;
    }

    @GetMapping(value = "get")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Object get(Authentication authentication){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();

        return details.getTokenValue();
    }
}
