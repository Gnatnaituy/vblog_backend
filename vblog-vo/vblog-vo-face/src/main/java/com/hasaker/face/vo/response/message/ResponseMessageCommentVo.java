package com.hasaker.face.vo.response.message;

import com.hasaker.face.vo.response.ResponseUserInfoVo;
import lombok.Data;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/5/3 21:04
 * @description ResponseMessageCommentVo
 */
@Data
public class ResponseMessageCommentVo {

    private Long id;

    private Long postId;

    private Long commentId;

    private String postSummary;

    private String commentSummary;

    private ResponseUserInfoVo createUser;

    private Long createTime;

    private Integer status;
}
