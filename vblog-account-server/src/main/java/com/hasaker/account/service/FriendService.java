package com.hasaker.account.service;

import com.hasaker.account.entity.Friend;
import com.hasaker.common.base.BaseService;
import com.hasaker.vo.account.request.RequestFriendAddVo;
import com.hasaker.vo.account.request.RequestFriendDeleteVo;
import com.hasaker.vo.account.request.RequestFriendRemarkVo;
import com.hasaker.vo.account.request.RequestFriendVisibilityVo;
import com.hasaker.vo.account.response.ResponseFriendVo;

import java.util.List;

/**
 * @package com.hasaker.account.service
 * @author 余天堂
 * @create 2020/3/2 10:14
 * @description FriendService
 */
public interface FriendService extends BaseService<Friend> {

    void addFriend(RequestFriendAddVo addFriendVo);

    void deleteFriend(RequestFriendDeleteVo deleteFriendVo);

    void changeVisibility(RequestFriendVisibilityVo changeVisibilityVo);

    void changeRemark(RequestFriendRemarkVo changeRemarkVo);

    List<ResponseFriendVo> listFriends(Long userId);
}
