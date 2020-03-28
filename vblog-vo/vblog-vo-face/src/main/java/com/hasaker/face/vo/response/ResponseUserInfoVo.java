package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/26 14:36
 * @description ResponseUserInfoVo
 */
@Data
@ApiModel(description = "用户信息")
@NoArgsConstructor
public class ResponseUserInfoVo {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "签名")
    private String bio;
}
