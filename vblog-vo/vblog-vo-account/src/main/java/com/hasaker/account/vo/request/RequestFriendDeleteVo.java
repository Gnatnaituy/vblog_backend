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
 * @create 2020/3/2 10:37
 * @description RequestDeleteFriendVo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = " Delete a friend")
public class RequestFriendDeleteVo {

    @NonNull
    @ApiModelProperty(value = "The user's ID", hidden = true)
    private Long userId;

    @NonNull
    @ApiModelProperty(value = "The friend's ID")
    private Long friendId;
}
