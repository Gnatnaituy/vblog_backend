package com.hasaker.account.service;

import com.hasaker.account.entity.FriendRequest;
import com.hasaker.account.vo.request.RequestFriendRequestAcceptVo;
import com.hasaker.account.vo.request.RequestFriendRequestVo;
import com.hasaker.common.base.BaseService;

/**
 * @package com.hasaker.account.service
 * @author 余天堂
 * @create 2020/3/19 22:45
 * @description FriendRequestHistoryService
 */
public interface FriendRequestService extends BaseService<FriendRequest> {

    void send(RequestFriendRequestVo requestVo);

    void accept(RequestFriendRequestAcceptVo acceptVo);

    void deny(Long friendRequestId);

    void ignore(Long friendRequestId);

    void indexAll();
}
