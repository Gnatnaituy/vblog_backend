package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/4/6 20:22
 * @description RequestChangePasswordVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Change password Vo")
public class RequestChangePasswordVo {

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "old password")
    private String oldPassword;

    @ApiModelProperty(value = "new password")
    private String newPassword;
}
