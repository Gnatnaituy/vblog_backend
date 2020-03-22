package com.hasaker.post.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * @package com.hasaker.post.vo.request
 * @author 余天堂
 * @create 2020/3/22 20:22
 * @description RequestPostImageVo
 */
@Data
@ApiModel(description = "Images for post")
public class RequestPostImageVo {

    @NonNull
    @ApiModelProperty(value = "Image's url")
    private String url;

    @NonNull
    @ApiModelProperty(value = "Image's order")
    private Integer order;
}
