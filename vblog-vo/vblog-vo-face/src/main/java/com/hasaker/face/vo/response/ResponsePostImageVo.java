package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/27 09:17
 * @description ResponsePostImageVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "image for post")
public class ResponsePostImageVo {

    @ApiModelProperty(value = "image ID")
    private Long id;

    @ApiModelProperty(value = "image url")
    private String url;

    @ApiModelProperty(value = "image sort")
    private Integer sort;
}
