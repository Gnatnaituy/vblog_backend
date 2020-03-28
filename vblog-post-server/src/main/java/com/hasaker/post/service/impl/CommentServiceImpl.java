package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.post.document.CommentDoc;
import com.hasaker.post.entity.Comment;
import com.hasaker.post.exception.enums.PostExceptionEnum;
import com.hasaker.post.mapper.CommentMapper;
import com.hasaker.post.service.CommentService;
import com.hasaker.post.vo.request.RequestCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        if (ObjectUtils.isNotNull(commentVo.getCommentId())) {
            commentVo.setPostId(null);
        } else {
            commentVo.setCommentId(null);
        }

        Comment comment = Convert.convert(Comment.class, commentVo);
        comment = this.saveId(comment);

        CommentDoc commentDoc = Convert.convert(CommentDoc.class, comment);
        commentDoc.setCommenter(comment.getCreateUser());
        commentDoc.setCommentTime(comment.getCreateTime());
        esService.index(commentDoc);
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
            CommentDoc commentDoc = esService.getById(comment.getId(), CommentDoc.class);
            CommentDoc replaceCommentDoc = Convert.convert(CommentDoc.class, replaceComment);
            replaceCommentDoc.setContent(COMMENT_DELETED);
            replaceCommentDoc.setCommenter(commentDoc.getCommenter());
            replaceCommentDoc.setCommentTime(commentDoc.getCommentTime());
            esService.index(replaceCommentDoc);
            // update parent comment's commentId that reply to this comment
            List<CommentDoc> commentDocs = esService.list(new Pair<>(Consts.COMMENT_ID, commentDoc.getId()), CommentDoc.class);
            if (ObjectUtils.isNotNull(commentDoc)) {
                esService.update(commentDocs.stream().map(CommentDoc::getId).collect(Collectors.toList()),
                        CommentDoc.class, new Pair<>(Consts.COMMENT_ID, replaceCommentDoc.getId()));
            }
        }

        this.removeById(commentId);
    }
}
