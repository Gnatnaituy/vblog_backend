package com.hasaker.oauth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hasaker.oauth.entity.WebUser;
import com.hasaker.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.oauth.service.impl
 * @author 余天堂
 * @create 2020/2/22 11:20
 * @description UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<WebUser> webUsers;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        webUsers = new ArrayList<>();
        webUsers.add(new WebUser("macro", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        webUsers.add(new WebUser("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        webUsers.add(new WebUser("mark", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public WebUser loadUserByUsername(String username) throws UsernameNotFoundException {
        List<WebUser> findWebUserList = webUsers.stream()
                .filter(webUser -> webUser.getUsername().equals(username))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(findWebUserList)) {
            return findWebUserList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
