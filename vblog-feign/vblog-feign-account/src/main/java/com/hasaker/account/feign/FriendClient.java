package com.hasaker.account.feign;

import com.hasaker.common.consts.Ajax;
import com.hasaker.vo.account.request.RequestFriendAddVo;
import com.hasaker.vo.account.request.RequestFriendDeleteVo;
import com.hasaker.vo.account.request.RequestFriendRemarkVo;
import com.hasaker.vo.account.request.RequestFriendVisibilityVo;
import com.hasaker.vo.account.response.ResponseFriendVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @package com.hasaker.account.feign
 * @author 余天堂
 * @create 2020/3/3 20:52
 * @description FriendClient
 */
@FeignClient(value = "vblog-account-server")
@RestController
public interface FriendClient {

    @PostMapping("/friend/add")
    Ajax addFriend(@RequestBody RequestFriendAddVo addFriendVo);

    @PostMapping("/friend/delete")
    Ajax deleteFriend(@RequestBody RequestFriendDeleteVo deleteFriendVo);

    @PostMapping("/friend/remark")
    Ajax changeRemark(@RequestBody RequestFriendRemarkVo changeRemarkVo);

    @PostMapping("/friend/visibility")
    Ajax changeVisibility(@RequestBody RequestFriendVisibilityVo changeVisibilityVo);

    @GetMapping("/friend/{userId}")
    Ajax<List<ResponseFriendVo>> listFriend(@PathVariable("userId") Long userId);
}
