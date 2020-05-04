package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/26 16:16
 * @description ResponseUserDetailVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "用户详情")
public class ResponseUserDetailVo {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "背景图")
    private String background;

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

    @ApiModelProperty(value = "创建时间")
    private Long registerTime;

    @ApiModelProperty(value = "屏蔽用户")
    private List<ResponseUserInfoVo> blocks;

    @ApiModelProperty(value = "用户创建的话题")
    private List<ResponseTopicInfoVo> topics;

    @ApiModelProperty(value = "用户热点词条")
    private List<String> words;

    @ApiModelProperty(value = "好友关系状态")
    private String friendStatus;

    @ApiModelProperty(value = "屏蔽关系状态")
    private Boolean blocked;
}
