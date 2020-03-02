package com.hasaker.vo.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.vo.user.request
 * @author 余天堂
 * @create 2020/3/2 10:37
 * @description RequestDeleteFriendVo
 */
@Data
@ApiModel(description = " 删除好友Vo")
public class RequestFriendDeleteVo {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "好友ID")
    private Long friendId;
}
