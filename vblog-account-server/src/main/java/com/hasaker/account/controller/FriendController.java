package com.hasaker.account.controller;

import com.hasaker.account.service.FriendRequestService;
import com.hasaker.account.service.FriendService;
import com.hasaker.account.vo.request.*;
import com.hasaker.account.vo.response.ResponseFriendVo;
import com.hasaker.common.vo.Ajax;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @package com.hasaker.account.controller
 * @author 余天堂
 * @create 2020/3/2 10:37
 * @description FriendshipController
 */
@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private FriendRequestService friendRequestService;

    @ApiOperation(value = "Send a friend request")
    @PostMapping(value = "/request/send")
    public Ajax sendFriendRequest(@RequestBody RequestFriendRequestVo requestVo) {
        friendRequestService.send(requestVo);

        return Ajax.success();
    }

    @ApiOperation(value = "Accept a friend request")
    @PostMapping(value = "/request/accept")
    public Ajax acceptFriendRequest(@RequestBody RequestFriendRequestAcceptVo acceptVo) {
        friendRequestService.accept(acceptVo);

        return Ajax.success();
    }

    @ApiOperation(value = "Deny a friend request")
    @PostMapping(value = "/request/deny/{friendRequestId}")
    public Ajax denyFriendRequest(@PathVariable Long friendRequestId) {
        friendRequestService.deny(friendRequestId);

        return Ajax.success();
    }

    @ApiOperation(value = "Ignore a friend request")
    @PostMapping(value = "/request/ignore/{friendRequestId}")
    public Ajax ignoreFriendRequest(@PathVariable Long friendRequestId) {
        friendRequestService.ignore(friendRequestId);

        return Ajax.success();
    }

    @ApiOperation(value = "Delete a friend")
    @PostMapping(value = "/delete")
    public Ajax deleteFriend(@RequestBody RequestFriendDeleteVo deleteVo) {
        friendService.delete(deleteVo);

        return Ajax.success();
    }

    @ApiOperation(value = "Change friend's remark")
    @PostMapping(value = "/remark")
    public Ajax changeRemark(@RequestBody RequestFriendRemarkVo remarkVo) {
        friendService.changeRemark(remarkVo);

        return Ajax.success();
    }

    @ApiOperation(value = "Change friend's visibility")
    @PostMapping(value = "/visibility")
    public Ajax changeVisibility(@RequestBody RequestFriendVisibilityVo visibilityVo) {
        friendService.changeVisibility(visibilityVo);

        return Ajax.success();
    }

    @ApiOperation(value = "List friends")
    @GetMapping(value = "/{userId}")
    public Ajax<List<ResponseFriendVo>> listFriend(@PathVariable("userId") Long userId) {
        return Ajax.getInstance().successT(friendService.listFriends(userId));
    }
}
