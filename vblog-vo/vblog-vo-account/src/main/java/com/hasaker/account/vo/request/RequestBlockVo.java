package com.hasaker.account.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.vo.user.request
 * @author 余天堂
 * @create 2020/3/2 10:38
 * @description RequestBlockUserVo
 */
@Data
@ApiModel(description = "屏蔽用户Vo")
public class RequestBlockVo {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户ID")
    private Long blockUserId;
}
