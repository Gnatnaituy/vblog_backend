package com.hasaker.post.service;

import com.hasaker.common.base.BaseService;
import com.hasaker.post.entity.Comment;
import com.hasaker.post.vo.request.RequestCommentVo;

/**
 * @package com.hasaker.post.service
 * @author 余天堂
 * @create 2020/3/22 19:39
 * @description CommentService
 */
public interface CommentService extends BaseService<Comment> {

    void comment(RequestCommentVo commentVo);

    void delete(Long commentId);
}
