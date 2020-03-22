package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hasaker.account.entity.Friend;
import com.hasaker.account.enums.VisibilityEnums;
import com.hasaker.account.exception.enums.FriendExceptionEnums;
import com.hasaker.account.mapper.FriendMapper;
import com.hasaker.account.service.FriendService;
import com.hasaker.account.vo.request.RequestFriendDeleteVo;
import com.hasaker.account.vo.request.RequestFriendRemarkVo;
import com.hasaker.account.vo.request.RequestFriendVisibilityVo;
import com.hasaker.account.vo.response.ResponseFriendVo;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.account.service.impl
 * @author 余天堂
 * @create 2020/3/2 10:15
 * @description FriendServiceImpl
 */
@Service
public class FriendServiceImpl extends BaseServiceImpl<FriendMapper, Friend> implements FriendService {

    /**
     * Insert a friend record
     * @param userId
     * @param friendId
     * @param remark
     * @param visibility
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, Long friendId, String remark, String visibility) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(friendId);

        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setRemark(remark);
        friend.setVisibility(visibility != null ? visibility : VisibilityEnums.VISIBLE_FOR_BOTH.getCode());

        this.save(friend);
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
        queryWrapper.eq(Friend.USER_ID, deleteVo.getUserId());
        queryWrapper.eq(Friend.FRIEND_ID, deleteVo.getFriendId());

        this.remove(queryWrapper);
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
    }

    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    @Override
    public List<ResponseFriendVo> listFriends(Long userId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Friend.USER_ID, userId);
        List<Friend> friends = this.list(queryWrapper);

        if (ObjectUtil.isNotEmpty(friends)) {
            return friends.stream()
                    .map(o -> Convert.convert(ResponseFriendVo.class, o))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
