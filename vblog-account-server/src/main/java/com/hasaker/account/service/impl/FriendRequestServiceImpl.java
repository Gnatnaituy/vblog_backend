package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.hasaker.account.document.FriendRequestDoc;
import com.hasaker.account.entity.FriendRequest;
import com.hasaker.account.entity.User;
import com.hasaker.account.enums.FriendRequestStatusEnum;
import com.hasaker.account.exception.enums.FriendExceptionEnums;
import com.hasaker.account.exception.enums.UserExceptionEnums;
import com.hasaker.account.mapper.FriendRequestMapper;
import com.hasaker.account.service.FriendRequestService;
import com.hasaker.account.service.FriendService;
import com.hasaker.account.service.UserService;
import com.hasaker.account.vo.request.RequestFriendRequestAcceptVo;
import com.hasaker.account.vo.request.RequestFriendRequestVo;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @package com.hasaker.account.service.impl
 * @author 余天堂
 * @create 2020/3/19 22:45
 * @description FriendRequestHistoryServiceImpl
 */
@Service
public class FriendRequestServiceImpl extends BaseServiceImpl<FriendRequestMapper, FriendRequest>
        implements FriendRequestService {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private EsService esService;


    /**
     * Send a friend request
     * @param requestVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(RequestFriendRequestVo requestVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(requestVo);

        User sender = userService.getById(requestVo.getSenderId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(sender);
        User receiver = userService.getById(requestVo.getReceiverID());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(receiver);

        // Generate a receiver request history
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(sender.getId());
        friendRequest.setSenderRemark(requestVo.getSenderRemark());
        friendRequest.setSenderVisibility(requestVo.getSenderVisibility());
        friendRequest.setReceiverId(receiver.getId());
        friendRequest.setRequestStatus(FriendRequestStatusEnum.NOT_READ.getCode());
        friendRequest = this.saveId(friendRequest);

        // save friend request to elasticsearch
        FriendRequestDoc friendRequestDoc = Convert.convert(FriendRequestDoc.class, friendRequest);
        friendRequestDoc.setId(String.valueOf(friendRequest.getId()));
        friendRequestDoc.setSenderId(String.valueOf(friendRequest.getSenderId()));
        friendRequestDoc.setReceiverId(String.valueOf(friendRequest.getReceiverId()));
        esService.index(friendRequestDoc);

        // Save friend request to redis
    }

    /**
     * Accept a friend request
     * Add friend record for both sender and accepter
     * @param acceptVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accept(RequestFriendRequestAcceptVo acceptVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(acceptVo);

        FriendRequest friendRequest = this.getById(acceptVo.getFriendRequestId());
        FriendExceptionEnums.FRIEND_REQUEST_NOT_EXISTS.assertNotEmpty(friendRequest);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.ACCEPTED.getCode());
        this.updateById(friendRequest);

        User sender = userService.getById(friendRequest.getSenderId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(sender);
        User receiver = userService.getById(friendRequest.getReceiverId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(receiver);

        // update friend request status in elasticsearch
        esService.update(String.valueOf(friendRequest.getId()), FriendRequestDoc.class,
                new Pair<>(FriendRequestDoc.REQUEST_STATUS, FriendRequestStatusEnum.ACCEPTED.getCode()));

        // Add friend for receiver
        friendService.add(receiver.getId(), sender.getId(), acceptVo.getAccepterRemark(), acceptVo.getAccepterVisibility());
        // Add friend for sender
        friendService.add(sender.getId(), receiver.getId(), friendRequest.getSenderRemark(), friendRequest.getSenderVisibility());
    }

    /**
     * Deny a friend request
     * @param friendRequestId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deny(Long friendRequestId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(friendRequestId);

        FriendRequest friendRequest = this.getById(friendRequestId);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.DENIED.getCode());
        this.updateById(friendRequest);

        // update friend request status in elasticsearch
        esService.update(String.valueOf(friendRequest.getId()), FriendRequestDoc.class,
                new Pair<>(FriendRequestDoc.REQUEST_STATUS, FriendRequestStatusEnum.DENIED.getCode()));
    }

    /**
     * Ignore a friend request
     * @param friendRequestId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ignore(Long friendRequestId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(friendRequestId);

        FriendRequest friendRequest = this.getById(friendRequestId);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.IGNORED.getCode());

        this.updateById(friendRequest);

        // update friend request status in elasticsearch
        esService.update(String.valueOf(friendRequest.getId()), FriendRequestDoc.class,
                new Pair<>(FriendRequestDoc.REQUEST_STATUS, FriendRequestStatusEnum.IGNORED.getCode()));
    }
}
