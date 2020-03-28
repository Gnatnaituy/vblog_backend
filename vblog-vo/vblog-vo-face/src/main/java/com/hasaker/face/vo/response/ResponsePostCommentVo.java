package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ApiModelProperty(value = "the post's ID which comment to")
    private Long postId;

    @ApiModelProperty(value = "the comment's ID which reply to")
    private Long commentId;

    @ApiModelProperty(value = "the content of the comment")
    private String content;

    @ApiModelProperty(value = "the replies of the comment")
    private List<ResponsePostCommentVo> replies;

    @ApiModelProperty(value = "commenter")
    private ResponseUserInfoVo commenter;

    @ApiModelProperty(value = "comment time")
    private Long commentTime;
}
