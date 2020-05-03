package com.hasaker.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hasaker.account.document.UserDoc;
import com.hasaker.account.entity.User;
import com.hasaker.account.entity.UserRole;
import com.hasaker.account.enums.RoleEnums;
import com.hasaker.account.exception.enums.UserExceptionEnums;
import com.hasaker.account.mapper.UserMapper;
import com.hasaker.account.service.RoleService;
import com.hasaker.account.service.UserRoleService;
import com.hasaker.account.service.UserService;
import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.vblog.service.impl
 * @author 余天堂
 * @create 2020/1/2 17:21
 * @description UserServiceImpl
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private EsService esService;

    /**
     * 获取用户登录信息
     * @param username
     * @return
     */
    @Override
    public ResponseUserOAuthVo findUserByUserName(String username) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(StringUtils.isBlank(username));

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.USERNAME, username);
        User user = this.getOne(queryWrapper);
        UserExceptionEnums.USER_NOT_EXISTS.isTrue(ObjectUtil.isNull(user));
        List<String> roles = roleService.getRolesByUserId(user.getId());

        ResponseUserOAuthVo userOAuthVo = new ResponseUserOAuthVo();
        userOAuthVo.setUserId(user.getId());
        userOAuthVo.setUsername(user.getUsername());
        userOAuthVo.setPassword(user.getPassword());
        userOAuthVo.setNickname(user.getNickname());
        userOAuthVo.setAvatar(user.getAvatar());
        userOAuthVo.setBackground(user.getBackground());
        userOAuthVo.setBio(user.getBio());
        userOAuthVo.setRoles(roles);

        return userOAuthVo;
    }

    /**
     * 创建用户
     * @param username
     * @param password
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(String username, String password) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(username);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(password);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = this.getOne(userQueryWrapper);
        UserExceptionEnums.USERNAME_ALREADY_EXISTS.assertEmpty(user);

        // create a user
        user = new User(username, password);
        user.setNickname(username);
        user.setAvatar(Consts.DEFAULT_AVATAR);
        user.setBackground(Consts.DEFAULT_USER_BACKGROUND);
        user = this.saveId(user);

        // assign default role to user
        UserRole userRole = new UserRole(user.getId(), roleService.getRoleIdByRoleName(RoleEnums.ROLE_USER.getCode()));
        userRoleService.save(userRole);

        // save user to es
        UserDoc userDoc = Convert.convert(UserDoc.class, user);
        userDoc.setRegisterTime(user.getCreateTime());
        userDoc.setBlocks(new HashSet<>());
        esService.index(userDoc);
    }

    /**
     * 用户修改密码
     * @param username
     * @param password
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String username, String password) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(username);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(password);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.USERNAME, username);
        User user = this.getOne(queryWrapper);
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(user);

        user.setPassword(password);
        this.updateById(user);
    }

    /**
     * 用户更新资料
     * @param userUpdateVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(RequestUserUpdateVo userUpdateVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userUpdateVo.getUserId());

        User user = this.getById(userUpdateVo.getUserId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(user);

        BeanUtil.copyProperties(userUpdateVo, user);
        this.updateById(user);

        // Update user's information in es
        UserDoc userDoc = esService.getById(user.getId(), UserDoc.class);
        BeanUtil.copyProperties(user, userDoc);
        esService.index(userDoc);
    }

    /**
     * Index all user information to ES
     */
    @Override
    public void indexAll() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List<User> users = this.list(userQueryWrapper);

        if (ObjectUtil.isNotNull(users)) {
            List<UserDoc> userDocs = users.stream().map(o -> {
                UserDoc userDoc = Convert.convert(UserDoc.class, o);
                userDoc.setRegisterTime(o.getCreateTime());
                return userDoc;
            }).collect(Collectors.toList());
            esService.deleteIndex(UserDoc.class);
            esService.createIndex(UserDoc.class);
            esService.index(userDocs);
        }
    }
}
