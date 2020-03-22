package com.hasaker.post.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.post.vo.request
 * @author 余天堂
 * @create 2020/3/22 20:56
 * @description RequestTopicVo
 */
@Data
@ApiModel(description = "Update topic's description")
public class RequestTopicVo {

    @ApiModelProperty(value = "Topic's ID")
    private Long topicId;

    @ApiModelProperty(value = "Topic's description")
    private String description;
}
