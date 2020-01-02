package com.hasaker.vblog.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hasaker.vblog.common.Consts;
import com.hasaker.vblog.entity.Menu;
import com.hasaker.vblog.entity.Role;
import com.hasaker.vblog.enums.RoleEnums;
import com.hasaker.vblog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @package com.hasaker.vblog.config
 * @author 余天堂
 * @create 2020/1/2 17:49
 * @description UrlFilterInvocationSecurityMetadataSource
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求地址
        // TODO 此处可优化 从Redis中读取 减少数据库访问次数
        String requestUrt = ((FilterInvocation) object).getRequestUrl();
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq(Consts.IS_DELETED, Consts.UNDELETED);
        List<Menu> menus = menuService.list(menuQueryWrapper);
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getUrl(), requestUrt)) {
                List<Role> menuRoles = menu.getRoles();
                String[] roles = menuRoles.stream().map(Role::getRoleName).toArray(String[]::new);

                return SecurityConfig.createList(roles);
            }
        }

        return SecurityConfig.createList(RoleEnums.GUEST.getCode());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
