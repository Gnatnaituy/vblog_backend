package com.hasaker.account.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.vo.user.response
 * @author 余天堂
 * @create 2020/3/2 11:51
 * @description ResponseFriendshipVo
 */
@Data
@ApiModel(description = "好友关系Vo")
public class ResponseFriendVo {

    @ApiModelProperty(value = "好友ID")
    private Long friendId;

    @ApiModelProperty(value = "好友备注")
    private String remark;

    @ApiModelProperty(value = "好友权限")
    private String visibility;
}
