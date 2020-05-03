package com.hasaker.account.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @package com.hasaker.account.vo.request
 * @author 余天堂
 * @create 2020/3/22 16:34
 * @description RequestFriendRequestAcceptVo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = "Accept a friend request")
public class RequestFriendRequestAcceptVo {

    @NonNull
    @ApiModelProperty(value = "The ID of the friend request record")
    private Long friendRequestId;

    @ApiModelProperty(value = "The remark set by accepter for sender")
    private String accepterRemark;

    @NonNull
    @ApiModelProperty(value = "The visibility set by accepter for sender")
    private Integer accepterVisibility;
}
