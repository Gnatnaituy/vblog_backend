package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.post.entity.Comment;
import com.hasaker.post.exception.enums.PostExceptionEnum;
import com.hasaker.post.mapper.CommentMapper;
import com.hasaker.post.service.CommentService;
import com.hasaker.post.vo.request.RequestCommentVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description CommentServiceImpl
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment> implements CommentService {

    /**
     * Write a comment
     * @param commentVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void comment(RequestCommentVo commentVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(commentVo);

        Comment comment = Convert.convert(Comment.class, commentVo);

        this.save(comment);
    }

    /**
     * Delete a comment
     * @param commentId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long commentId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(commentId);

        Comment comment = this.getById(commentId);
        PostExceptionEnum.COMMENT_NOT_EXISTS.assertNotEmpty(comment);

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq(Comment.COMMENT_ID, commentId);
        List<Comment> comments = this.list(commentQueryWrapper);

        if (ObjectUtils.isNotNull(comments)) {
            Comment replaceComment = new Comment();
            replaceComment.setPostId(comment.getPostId());
            replaceComment.setCommentId(comment.getCommentId());
            replaceComment.setContent("This comment had been deleted");
            replaceComment = this.saveId(replaceComment);

            Comment updateComment = new Comment();
            updateComment.setCommentId(replaceComment.getId());
            this.update(updateComment, commentQueryWrapper);
        }

        this.removeById(commentId);
    }
}
