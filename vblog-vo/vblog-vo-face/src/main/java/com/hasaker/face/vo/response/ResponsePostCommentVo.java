package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/27 09:18
 * @description ResponsePostCommentVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "comment for post")
public class ResponsePostCommentVo {

    @ApiModelProperty(value = "comment's ID")
    private Long id;

    @ApiModelProperty(value = "the content of the comment")
    private String content;

    @ApiModelProperty(value = "the post's ID which been commented")
    private Long postId;

    @ApiModelProperty(value = "the comment's ID which been replied")
    private Long commentId;

    @ApiModelProperty(value = "the commenter which been replied")
    private ResponseUserInfoVo originCommenter;

    @ApiModelProperty(value = "commenter")
    private ResponseUserInfoVo commenter;

    @ApiModelProperty(value = "comment time")
    private Long commentTime;
}
