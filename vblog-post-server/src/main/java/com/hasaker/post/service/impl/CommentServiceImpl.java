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

import java.util.HashSet;
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

    private static final String COMMENT_DELETED = "This comment had been deleted";

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
        commentDoc.setReplies(new HashSet<>());
        esService.index(commentDoc);

        if (ObjectUtils.isNotNull(comment.getCommentId())) {
            CommentDoc parent = esService.getById(String.valueOf(comment.getCommentId()), CommentDoc.class);
            parent.getReplies().add(commentDoc.getId());
            esService.update(parent.getId(), CommentDoc.class, new Pair<>("replies", parent.getReplies()));
        } else {
            PostDoc postDoc = esService.getById(String.valueOf(comment.getPostId()), PostDoc.class);
            postDoc.getComments().add(commentDoc.getId());
            esService.update(postDoc.getId(), PostDoc.class, new Pair<>("comments", postDoc.getComments()));
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
        List<Comment> replies = this.list(commentQueryWrapper);

        if (ObjectUtils.isNotNull(replies)) {
            // replace the comment
            Comment replaceComment = new Comment();
            replaceComment.setPostId(comment.getPostId());
            replaceComment.setCommentId(comment.getCommentId());
            replaceComment.setContent(COMMENT_DELETED);
            replaceComment = this.saveId(replaceComment);

            // update the reply's commentId belong to this comment
            Comment updateComment = new Comment();
            updateComment.setCommentId(replaceComment.getId());
            this.update(updateComment, commentQueryWrapper);

            // update comments in es
            CommentDoc commentDoc = esService.getById(String.valueOf(comment.getId()), CommentDoc.class);
            CommentDoc replaceCommentDoc = new CommentDoc();
            replaceCommentDoc.setId(String.valueOf(replaceComment.getId()));
            replaceCommentDoc.setPostId(String.valueOf(replaceComment.getPostId()));
            replaceCommentDoc.setCommentId(String.valueOf(replaceComment.getCommentId()));
            replaceCommentDoc.setContent(COMMENT_DELETED);
            replaceCommentDoc.setReplies(commentDoc.getReplies());
            replaceCommentDoc.setVotes(commentDoc.getVotes());
            replaceCommentDoc.setDownvotes(commentDoc.getDownvotes());
            esService.index(replaceCommentDoc);
            // update parent comment's replies or post's comments
            if (ObjectUtils.isNotNull(commentDoc.getCommentId())) {
                CommentDoc parentCommentDoc = esService.getById(commentDoc.getCommentId(), CommentDoc.class);
                parentCommentDoc.getReplies().remove(commentDoc.getId());
                parentCommentDoc.getReplies().add(replaceCommentDoc.getId());
                esService.update(parentCommentDoc.getId(), CommentDoc.class,
                        new Pair<>(CommentDoc.REPLIES, parentCommentDoc.getReplies()));
            } else {
                PostDoc postDoc = esService.getById(commentDoc.getPostId(), PostDoc.class);
                postDoc.getComments().remove(commentDoc.getId());
                postDoc.getComments().add(replaceCommentDoc.getId());
                esService.update(postDoc.getId(), PostDoc.class, new Pair<>(PostDoc.COMMENTS, postDoc.getComments()));
            }
        }

        this.removeById(commentId);
    }
}
