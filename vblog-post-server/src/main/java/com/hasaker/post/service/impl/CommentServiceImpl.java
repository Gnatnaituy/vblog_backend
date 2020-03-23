package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.post.document.CommentDoc;
import com.hasaker.post.document.PostDoc;
import com.hasaker.post.entity.Comment;
import com.hasaker.post.exception.enums.PostExceptionEnum;
import com.hasaker.post.mapper.CommentMapper;
import com.hasaker.post.service.CommentService;
import com.hasaker.post.vo.request.RequestCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description CommentServiceImpl
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private EsService esService;

    /**
     * Write a comment
     * @param commentVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void comment(RequestCommentVo commentVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(commentVo);

        Comment comment = Convert.convert(Comment.class, commentVo);
        comment = this.saveId(comment);

        CommentDoc commentDoc = Convert.convert(CommentDoc.class, comment);
        commentDoc.setReplies(new ArrayList<>());
        esService.index(commentDoc);
        if (ObjectUtils.isNotNull(comment.getPostId())) {
            PostDoc postDoc = esService.getById(String.valueOf(comment.getPostId()), PostDoc.class);
            postDoc.getComments().add(String.valueOf(commentDoc.getId()));
            esService.update(postDoc.getId(), PostDoc.class, new Pair<>("comments", postDoc.getComments()));
        } else {
            CommentDoc parent = esService.getById(String.valueOf(comment.getCommentId()), CommentDoc.class);
            parent.getReplies().add(commentDoc.getId());
            esService.update(parent.getId(), CommentDoc.class, new Pair<>("replies", parent.getReplies()));
        }
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

            esService.update(String.valueOf(comment.getCommentId()), CommentDoc.class,
                    new Pair<>("content", "This comment had been deleted"));
        }

        this.removeById(commentId);
    }
}
