package com.hasaker.face.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/26 16:16
 * @description ResponsePostVo
 */
@Data
@NoArgsConstructor
public class ResponsePostVo {

    @ApiModelProperty(value = "post's ID")
    private Long id;

    @ApiModelProperty(value = "the content of this post")
    private String content;

    @ApiModelProperty(value = "images of this post")
    private List<ResponsePostImageVo> images;

    @ApiModelProperty(value = "topics of this post")
    private List<ResponsePostTopicVo> topics;

    @ApiModelProperty(value = "comments of this post")
    private List<ResponsePostCommentVo> comments;

    @ApiModelProperty(value = "comments of this post")
    private List<ResponseUserInfoVo> voters;

    @ApiModelProperty(value = "poster")
    private ResponseUserInfoVo poster;

    @ApiModelProperty(value = "post time")
    private Long postTime;

    @ApiModelProperty(value = "Is voted by current user")
    private Boolean voteByMe;
}
