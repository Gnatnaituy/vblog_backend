package com.hasaker.account.feign;

import com.hasaker.account.vo.request.*;
import com.hasaker.common.vo.Ajax;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.account.feign
 * @author 余天堂
 * @create 2020/3/3 20:52
 * @description FriendClient
 */
@FeignClient(value = "friend-client", url = "127.0.0.1:9001")
@RestController
public interface FriendClient {

    @PostMapping("/friend/request/send")
    Ajax sendFriendRequest(@RequestBody RequestFriendRequestVo addFriendVo);

    @PostMapping("/friend/request/accept")
    Ajax acceptFriendRequest(@RequestBody RequestFriendRequestAcceptVo acceptVo);

    @PostMapping("/friend/request/deny/{friendRequestId}")
    Ajax denyFriendRequest(@PathVariable("friendRequestId") Long friendRequestId);

    @PostMapping("/friend/request/ignore/{friendRequestId}")
    Ajax ignoreFriendRequest(@PathVariable("friendRequestId") Long friendRequestId);

    @PostMapping("/friend/delete")
    Ajax deleteFriend(@RequestBody RequestFriendDeleteVo deleteVo);

    @PostMapping("/friend/remark")
    Ajax changeRemark(@RequestBody RequestFriendRemarkVo remarkVo);

    @PostMapping("/friend/visibility")
    Ajax changeVisibility(@RequestBody RequestFriendVisibilityVo visibilityVo);
}
