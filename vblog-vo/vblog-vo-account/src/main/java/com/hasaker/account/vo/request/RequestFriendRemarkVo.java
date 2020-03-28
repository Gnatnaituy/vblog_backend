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
 * @create 2020/3/2 11:20
 * @description RequestChangeRemarkVo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = "Change friend's remark")
public class RequestFriendRemarkVo {

    @NonNull
    @ApiModelProperty(value = "The user's ID", hidden = true)
    private Long userId;

    @NonNull
    @ApiModelProperty(value = "The friend's ID")
    private Long friendId;

    @NonNull
    @ApiModelProperty(value = "The remark set by user for friend")
    private String remark;
}
