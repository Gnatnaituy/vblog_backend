package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hasaker.account.document.FriendDoc;
import com.hasaker.account.entity.Friend;
import com.hasaker.account.exception.enums.FriendExceptionEnums;
import com.hasaker.account.exception.enums.UserExceptionEnums;
import com.hasaker.account.mapper.FriendMapper;
import com.hasaker.account.service.FriendService;
import com.hasaker.account.service.UserService;
import com.hasaker.account.vo.request.RequestFriendDeleteVo;
import com.hasaker.account.vo.request.RequestFriendRemarkVo;
import com.hasaker.account.vo.request.RequestFriendVisibilityVo;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @package com.hasaker.account.service.impl
 * @author 余天堂
 * @create 2020/3/2 10:15
 * @description FriendServiceImpl
 */
@Service
public class FriendServiceImpl extends BaseServiceImpl<FriendMapper, Friend> implements FriendService {

    @Autowired
    private UserService userService;
    @Autowired
    private EsService esService;

    /**
     * Insert a friend record
     * @param userId
     * @param friendId
     * @param remark
     * @param visibility
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, Long friendId, String remark, Integer visibility) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(friendId);

        // Check if both user and friend exists
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(userService.getById(userId));
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(userService.getById(friendId));

        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setRemark(remark);
        friend.setVisibility(visibility != null ? visibility : Consts.FRIEND_BOTH);
        friend = this.saveId(friend);

        // index FriendDoc to es
        FriendDoc friendDoc = Convert.convert(FriendDoc.class, friend);
        friendDoc.setAddTime(friend.getCreateTime());
        esService.index(friendDoc);
    }

    /**
     * Delete a friend record
     * @param deleteVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(RequestFriendDeleteVo deleteVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(deleteVo);

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.or(o -> o.eq(Friend.USER_ID, deleteVo.getUserId()).eq(Friend.FRIEND_ID, deleteVo.getFriendId()));
        queryWrapper.or(o -> o.eq(Friend.FRIEND_ID, deleteVo.getUserId()).eq(Friend.USER_ID, deleteVo.getFriendId()));
        List<Friend> friends = this.list(queryWrapper);
        FriendExceptionEnums.FRIEND_NOT_EXISTS.assertNotEmpty(friends.size() != 2);
        this.remove(queryWrapper);

        // Delete FriendDoc fro es
        esService.delete(Arrays.asList(deleteVo.getUserId(), deleteVo.getFriendId()), FriendDoc.class);
    }

    /**
     * 改变好友可见性
     * @param visibilityVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeVisibility(RequestFriendVisibilityVo visibilityVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(visibilityVo);

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Friend.USER_ID, visibilityVo.getUserId());
        queryWrapper.eq(Friend.FRIEND_ID, visibilityVo.getFriendId());
        Friend friend = this.getOne(queryWrapper);
        FriendExceptionEnums.FRIEND_NOT_EXISTS.assertNotEmpty(friend);

        friend.setVisibility(visibilityVo.getVisibility());
        this.updateById(friend);

        // Update visibility in es
        esService.update(friend.getId(), FriendDoc.class, new Pair<>(FriendDoc.VISIBILITY, friend.getVisibility()));
    }

    /**
     * 修改好友昵称
     * @param remarkVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeRemark(RequestFriendRemarkVo remarkVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(remarkVo);

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Friend.USER_ID, remarkVo.getUserId());
        queryWrapper.eq(Friend.FRIEND_ID, remarkVo.getFriendId());
        Friend friend = this.getOne(queryWrapper);
        FriendExceptionEnums.FRIEND_NOT_EXISTS.assertNotEmpty(friend);

        friend.setRemark(remarkVo.getRemark());
        this.updateById(friend);

        // update remark in es
        esService.update(friend.getId(), FriendDoc.class, new Pair<>(FriendDoc.REMARK, friend.getRemark()));
    }
}
