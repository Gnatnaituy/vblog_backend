package com.hasaker.vo.account.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.vo.user.request
 * @author 余天堂
 * @create 2020/3/2 11:20
 * @description RequestChangeRemarkVo
 */
@Data
@ApiModel(description = "改变好友昵称Vo")
public class RequestFriendRemarkVo {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "好友ID")
    private Long friendId;

    @ApiModelProperty(value = "昵称")
    private String remark;
}
