package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/26 16:39
 * @description ResponseFriendInfoVo
 */
@Data
@ApiModel(description = "Friend's information")
@NoArgsConstructor
public class ResponseFriendInfoVo {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "可见性")
    private Integer visibility;
}
