package com.hasaker.post.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.post.vo.request
 * @author 余天堂
 * @create 2020/3/22 21:06
 * @description RequestCommentVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Write a comment")
public class RequestCommentVo {

    @ApiModelProperty(value = "Post's ID which will be commented")
    private Long postId;

    @ApiModelProperty(value = "Comment's ID which will be commented")
    private Long commentId;

    @ApiModelProperty(value = "Comment's content")
    private String content;
}
