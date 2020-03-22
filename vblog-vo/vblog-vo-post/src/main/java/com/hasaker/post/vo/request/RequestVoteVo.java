package com.hasaker.post.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package com.hasaker.post.vo.request
 * @author 余天堂
 * @create 2020/3/22 19:55
 * @description RequestVoteVo
 */
@Data
@ApiModel(description = "Vote and downvote Vo")
public class RequestVoteVo {

    @ApiModelProperty(value = "The post's ID")
    private Long postId;

    @ApiModelProperty(value = "The comment's ID")
    private Long commentId;
}
