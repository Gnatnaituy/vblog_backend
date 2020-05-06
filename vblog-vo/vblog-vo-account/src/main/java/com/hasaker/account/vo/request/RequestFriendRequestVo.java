package com.hasaker.account.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @package com.hasaker.vo.user.request
 * @author 余天堂
 * @create 2020/3/2 10:22
 * @description RequestFollowVo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = "Add friend request vo")
public class RequestFriendRequestVo {

    @NonNull
    @ApiModelProperty(value = "The sender's userId")
    private Long senderId;

    @ApiModelProperty(value = "The remark set by sender for friend")
    private String senderRemark;

    @ApiModelProperty(value = "The visibility set by sender for friend")
    private Integer senderVisibility;

    @NonNull
    @ApiModelProperty(value = "The friend's userId")
    private Long receiverId;
}
