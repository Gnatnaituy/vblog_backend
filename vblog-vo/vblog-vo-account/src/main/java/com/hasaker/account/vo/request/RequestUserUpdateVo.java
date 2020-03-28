package com.hasaker.account.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.vo.user.request
 * @author 余天堂
 * @create 2020/3/2 08:50
 * @description RequestUserCreateVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Update user's information")
public class RequestUserUpdateVo {

    @ApiModelProperty(value = "The user's ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "签名")
    private String bio;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;
}
