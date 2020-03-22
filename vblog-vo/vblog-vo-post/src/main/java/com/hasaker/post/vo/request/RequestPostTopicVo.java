package com.hasaker.post.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.post.vo.request
 * @author 余天堂
 * @create 2020/3/22 20:22
 * @description RequestPostTopicVo
 */
@Data
@ApiModel(description = "Topics for post")
public class RequestPostTopicVo {

    @ApiModelProperty(value = "Topic's ID if topic already exists")
    private Long topicId;

    @ApiModelProperty(value = "Topic's name if topic not exists")
    private String name;
}
