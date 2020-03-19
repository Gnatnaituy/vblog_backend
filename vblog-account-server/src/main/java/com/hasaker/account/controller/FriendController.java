package com.hasaker.account.controller;

import com.hasaker.account.service.FriendService;
import com.hasaker.account.vo.request.RequestFriendAddVo;
import com.hasaker.account.vo.request.RequestFriendDeleteVo;
import com.hasaker.account.vo.request.RequestFriendRemarkVo;
import com.hasaker.account.vo.request.RequestFriendVisibilityVo;
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

    @ApiOperation(value = "添加好友")
    @PostMapping(value = "/add")
    public Ajax addFriend(@RequestBody RequestFriendAddVo addFriendVo) {
        friendService.addFriend(addFriendVo);

        return Ajax.success();
    }

    @ApiOperation(value = "删除好友")
    @PostMapping(value = "/delete")
    public Ajax deleteFriend(@RequestBody RequestFriendDeleteVo deleteFriendVo) {
        friendService.deleteFriend(deleteFriendVo);

        return Ajax.success();
    }

    @ApiOperation(value = "修改好友备注")
    @PostMapping(value = "/remark")
    public Ajax changeRemark(@RequestBody RequestFriendRemarkVo changeRemarkVo) {
        friendService.changeRemark(changeRemarkVo);

        return Ajax.success();
    }

    @ApiOperation(value = "设置好友权限")
    @PostMapping(value = "/visibility")
    public Ajax changeVisibility(@RequestBody RequestFriendVisibilityVo changeVisibilityVo) {
        friendService.changeVisibility(changeVisibilityVo);

        return Ajax.success();
    }

    @ApiOperation(value = "获取好友列表")
    @GetMapping(value = "/{userId}")
    public Ajax<List<ResponseFriendVo>> listFriend(@PathVariable("userId") Long userId) {
        return Ajax.getInstance().successT(friendService.listFriends(userId));
    }
}
