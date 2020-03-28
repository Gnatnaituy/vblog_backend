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
 * @create 2020/3/2 10:38
 * @description RequestBlockUserVo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = "Block a user")
public class RequestBlockVo {

    @NonNull
    @ApiModelProperty(value = "The user's ID", hidden = true)
    private Long userId;

    @NonNull
    @ApiModelProperty(value = "The target user's ID")
    private Long blockUserId;
}
