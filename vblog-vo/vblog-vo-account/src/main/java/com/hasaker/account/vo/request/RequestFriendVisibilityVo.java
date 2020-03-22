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
 * @create 2020/3/2 11:17
 * @description RequestChangeVisibilityVo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = "Change visibility for a friend")
public class RequestFriendVisibilityVo {

    @NonNull
    @ApiModelProperty(value = "The user's ID")
    private Long userId;

    @NonNull
    @ApiModelProperty(value = "The friend's ID")
    private Long friendId;

    @NonNull
    @ApiModelProperty(value = "The visibility set by user for this friend")
    private String visibility;
}
