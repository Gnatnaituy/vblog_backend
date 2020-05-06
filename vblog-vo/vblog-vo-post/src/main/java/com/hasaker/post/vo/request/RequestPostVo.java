package com.hasaker.post.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @package com.hasaker.post.vo.request
 * @author 余天堂
 * @create 2020/3/22 20:21
 * @description RequestPostVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Create a post")
public class RequestPostVo {

    @ApiModelProperty(value = "Post's content")
    private String content;

    @ApiModelProperty(value = "Post's visibility")
    private String visibility;

    @ApiModelProperty(value = "Post's images")
    private List<RequestPostImageVo> images;

    @ApiModelProperty(value = "Post's topics")
    private List<RequestPostTopicVo> topics;
}
