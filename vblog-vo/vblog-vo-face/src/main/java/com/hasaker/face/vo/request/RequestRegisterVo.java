package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/4/6 20:21
 * @description RequestRegisterVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Register Vo")
public class RequestRegisterVo {

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;
}
