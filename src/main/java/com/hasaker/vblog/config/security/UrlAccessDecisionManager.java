package com.hasaker.vblog.config.security;

import com.hasaker.vblog.enums.RoleEnums;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.vblog.config.security
 * @author 余天堂
 * @create 2020/1/2 20:40
 * @description AccessDecisionManager
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            String needRole = configAttribute.getAttribute();
            // 游客权限 直接通过
            if (RoleEnums.GUEST.equalsStr(needRole)) {
                return;
            }

            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (RoleEnums.ADMIN.equalsStr(needRole)) {
                if (authorities.contains(needRole)) {
                    return;
                }
                throw new AccessDeniedException("你没有管理员权限!");
            }
            if (RoleEnums.USER.equalsStr(needRole)) {
                if (authorities.contains(needRole)) {
                    return;
                }
                throw new AccessDeniedException("请先登录!");
            }
        }

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
