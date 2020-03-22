package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hasaker.account.entity.User;
import com.hasaker.account.exception.enums.UserExceptionEnums;
import com.hasaker.account.mapper.UserMapper;
import com.hasaker.account.service.RoleService;
import com.hasaker.account.service.UserService;
import com.hasaker.account.vo.request.RequestUserSearchVo;
import com.hasaker.account.vo.request.RequestUserUpdateVo;
import com.hasaker.account.vo.response.ResponseUserDetailVo;
import com.hasaker.account.vo.response.ResponseUserOAuthVo;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private UserMapper userMapper;

    /**
     * 获取用户登录信息
     * @param username
     * @return
     */
    @Override
    public ResponseUserOAuthVo findUserByUserName(String username) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(StringUtils.isBlank(username));

        User user = userMapper.findUserByUserName(username);
        UserExceptionEnums.USER_NOT_EXISTS.isTrue(ObjectUtil.isNull(user));
        List<String> roles = roleService.getRolesByUserId(user.getId());

        ResponseUserOAuthVo userOAuthVo = new ResponseUserOAuthVo();
        userOAuthVo.setUserId(user.getId());
        userOAuthVo.setUsername(user.getUsername());
        userOAuthVo.setPassword(user.getPassword());
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

        user = new User();
        user.setUsername(username);
        user.setPassword(password);

        this.save(user);
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

        User user = userMapper.findUserByUserName(username);
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
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userUpdateVo.getUsername());

        User user = userMapper.findUserByUserName(userUpdateVo.getUsername());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(user);

        User updatedUser = Convert.convert(User.class, userUpdateVo);
        updatedUser.setId(user.getId());

        this.updateById(updatedUser);
    }

    /**
     * 根据用户名查询用户详情
     * @param username
     * @return
     */
    @Override
    public ResponseUserDetailVo userDetail(String username) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(username);

        User user = userMapper.findUserByUserName(username);
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(user);

        return Convert.convert(ResponseUserDetailVo.class, user);
    }

    /**
     * 搜索用户
     * @param searchVo
     * @return
     */
    @Override
    public IPage<ResponseUserDetailVo> list(RequestUserSearchVo searchVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchVo);

        IPage<User> page = new Page<>();
        page.setCurrent(searchVo.getCurrent());
        page.setSize(searchVo.getSize());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotNull(searchVo.getKeyword())) {
            queryWrapper.and(o -> o.like(User.USERNAME, searchVo.getKeyword())
                    .or().like(User.NICKNAME, searchVo.getKeyword())
                    .or().eq(User.EMAIL, searchVo.getKeyword())
                    .or().eq(User.PHONE, searchVo.getKeyword()));
        }
        if (ObjectUtils.isNotNull(searchVo.getGender())) {
            queryWrapper.eq(User.GENDER, searchVo.getKeyword());
        }
        if (ObjectUtils.isNotNull(searchVo.getMinAge())) {
            queryWrapper.ge(User.AGE, searchVo.getMinAge());
        }
        if (ObjectUtils.isNotNull(searchVo.getMaxAge())) {
            queryWrapper.le(User.AGE, searchVo.getMaxAge());
        }
        IPage<User> userIPage = this.page(page, queryWrapper);

        return userIPage.convert(o -> Convert.convert(ResponseUserDetailVo.class, o));
    }
}
