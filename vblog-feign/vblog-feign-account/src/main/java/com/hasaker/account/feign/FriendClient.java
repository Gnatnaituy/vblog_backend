package com.hasaker.account.feign;

import com.hasaker.account.vo.request.RequestFriendDeleteVo;
import com.hasaker.account.vo.request.RequestFriendRemarkVo;
import com.hasaker.account.vo.request.RequestFriendRequestVo;
import com.hasaker.account.vo.request.RequestFriendVisibilityVo;
import com.hasaker.account.vo.response.ResponseFriendVo;
import com.hasaker.common.vo.Ajax;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @package com.hasaker.account.feign
 * @author 余天堂
 * @create 2020/3/3 20:52
 * @description FriendClient
 */
@FeignClient(value = "friend-client", url = "127.0.0.1:9001")
@RestController
public interface FriendClient {

    @PostMapping("/friend/add")
    Ajax addFriend(@RequestBody RequestFriendRequestVo addFriendVo);

    @PostMapping("/friend/delete")
    Ajax deleteFriend(@RequestBody RequestFriendDeleteVo deleteFriendVo);

    @PostMapping("/friend/remark")
    Ajax changeRemark(@RequestBody RequestFriendRemarkVo changeRemarkVo);

    @PostMapping("/friend/visibility")
    Ajax changeVisibility(@RequestBody RequestFriendVisibilityVo changeVisibilityVo);

    @GetMapping("/friend/{userId}")
    Ajax<List<ResponseFriendVo>> listFriend(@PathVariable("userId") Long userId);
}
