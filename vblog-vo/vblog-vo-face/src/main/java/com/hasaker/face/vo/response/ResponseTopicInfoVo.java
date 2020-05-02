package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/27 23:07
 * @description ResponsePostTopicVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Topic")
public class ResponseTopicInfoVo {

    @ApiModelProperty(value = "Topic's ID")
    private Long id;

    @ApiModelProperty(value = "Topic's name")
    private String name;
}
