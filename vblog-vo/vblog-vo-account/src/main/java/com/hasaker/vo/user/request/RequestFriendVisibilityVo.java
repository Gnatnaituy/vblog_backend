package com.hasaker.vo.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.vo.user.request
 * @author 余天堂
 * @create 2020/3/2 11:17
 * @description RequestChangeVisibilityVo
 */
@Data
@ApiModel(description = "改变好友可见性Vo")
public class RequestFriendVisibilityVo {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "好友ID")
    private Long friendId;

    @ApiModelProperty(value = "可见性")
    private String visibility;
}
