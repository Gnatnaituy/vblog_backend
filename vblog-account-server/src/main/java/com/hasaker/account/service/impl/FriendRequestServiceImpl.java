package com.hasaker.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
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
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        // Check if both sender and receiver exists
        User sender = userService.getById(requestVo.getSenderId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(sender);
        User receiver = userService.getById(requestVo.getReceiverId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(receiver);

        // Generate a receiver request history
        FriendRequest friendRequest = Convert.convert(FriendRequest.class, requestVo);
        friendRequest.setSenderId(sender.getId());
        friendRequest.setReceiverId(receiver.getId());
        friendRequest.setSenderVisibility(ObjectUtils.isNotNull(requestVo.getSenderVisibility())
                ? requestVo.getSenderVisibility() : Consts.FRIEND_BOTH);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.NOT_READ.getCode());
        friendRequest = this.saveId(friendRequest);

        // Save friend request to elasticsearch
        FriendRequestDoc friendRequestDoc = Convert.convert(FriendRequestDoc.class, friendRequest);
        friendRequestDoc.setSenderId(friendRequest.getSenderId());
        friendRequestDoc.setReceiverId(friendRequest.getReceiverId());
        friendRequestDoc.setSendTime(friendRequest.getCreateTime());
        esService.index(friendRequestDoc);
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

        // Change friend request's status
        FriendRequest friendRequest = this.getById(acceptVo.getFriendRequestId());
        FriendExceptionEnums.FRIEND_REQUEST_NOT_EXISTS.assertNotEmpty(friendRequest);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.ACCEPTED.getCode());
        this.updateById(friendRequest);

        User sender = userService.getById(friendRequest.getSenderId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(sender);
        User receiver = userService.getById(friendRequest.getReceiverId());
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(receiver);

        // Update friend request status in elasticsearch
        esService.update(friendRequest.getId(), FriendRequestDoc.class, Arrays.asList(
                        new Pair<>(FriendRequestDoc.REQUEST_STATUS, FriendRequestStatusEnum.ACCEPTED.getCode()),
                        new Pair<>(FriendRequestDoc.ACCEPT_TIME, friendRequest.getUpdateTime())));

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

        // Change friend request's status
        FriendRequest friendRequest = this.getById(friendRequestId);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.DENIED.getCode());
        this.updateById(friendRequest);

        // update friend request status in elasticsearch
        esService.update(friendRequest.getId(), FriendRequestDoc.class, Arrays.asList(
                new Pair<>(FriendRequestDoc.REQUEST_STATUS, FriendRequestStatusEnum.DENIED.getCode()),
                new Pair<>(FriendRequestDoc.DENY_TIME, friendRequest.getUpdateTime())));
    }

    /**
     * Ignore a friend request
     * @param friendRequestId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ignore(Long friendRequestId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(friendRequestId);

        // Change friend request's status
        FriendRequest friendRequest = this.getById(friendRequestId);
        friendRequest.setRequestStatus(FriendRequestStatusEnum.IGNORED.getCode());
        this.updateById(friendRequest);

        // update friend request status in elasticsearch
        esService.update(friendRequest.getId(), FriendRequestDoc.class, Arrays.asList(
                new Pair<>(FriendRequestDoc.REQUEST_STATUS, FriendRequestStatusEnum.IGNORED.getCode()),
                new Pair<>(FriendRequestDoc.IGNORE_TIME, friendRequest.getUpdateTime())));
    }

    /**
     * Index all friend to ES
     */
    @Override
    public void indexAll() {
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        List<FriendRequest> friendRequests = this.list(queryWrapper);

        esService.deleteIndex(FriendRequestDoc.class);
        esService.createIndex(FriendRequestDoc.class);
        if (ObjectUtils.isNotNull(friendRequests)) {
            List<FriendRequestDoc> friendRequestDocs = friendRequests.stream().map(o -> {
                FriendRequestDoc friendRequestDoc = Convert.convert(FriendRequestDoc.class, o);
                friendRequestDoc.setSendTime(o.getCreateTime());
                if (FriendRequestStatusEnum.ACCEPTED.equalStr(o.getRequestStatus())) {
                    friendRequestDoc.setAcceptTime(o.getUpdateTime());
                } else if (FriendRequestStatusEnum.DENIED.equalStr(o.getRequestStatus())) {
                    friendRequestDoc.setDenyTime(o.getUpdateTime());
                } else if (FriendRequestStatusEnum.IGNORED.equalStr(o.getRequestStatus())) {
                    friendRequestDoc.setIgnoreTime(o.getUpdateTime());
                }
                return friendRequestDoc;
            }).collect(Collectors.toList());
            esService.index(friendRequestDocs);
        }
    }
}
