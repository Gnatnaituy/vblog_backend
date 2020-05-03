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
import com.hasaker.post.message.CommentMessageDoc;
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

    /**
     * Write a comment
     * @param commentVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long comment(RequestCommentVo commentVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(commentVo);

        Comment comment = Convert.convert(Comment.class, commentVo);
        comment = this.saveId(comment);
        comment = this.getById(comment.getId());

        // Save comment to ES
        CommentDoc commentDoc = Convert.convert(CommentDoc.class, comment);
        commentDoc.setDeleted(Consts.NO);
        commentDoc.setCommenter(comment.getCreateUser());
        commentDoc.setCommentTime(comment.getCreateTime());
        esService.index(commentDoc);

        // Save comment message to ES
        CommentMessageDoc commentMessageDoc = Convert.convert(CommentMessageDoc.class, comment);
        commentMessageDoc.setReceiver(comment.getCreateUser());
        commentMessageDoc.setStatus(Consts.MESSAGE_STATUS_UNREAD);
        esService.index(commentMessageDoc);

        return comment.getId();
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
        comment.setDeleted(Consts.YES);
        this.updateById(comment);

        esService.update(commentId, CommentDoc.class, new Pair<>(CommentDoc.DELETED, 1));
    }

    /**
     * Index all comments to es for dev use
     */
    @Override
    public void indexAllComments() {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        List<Comment> comments = this.list(commentQueryWrapper);

        if (ObjectUtils.isNotNull(comments)) {
            List<CommentDoc> commentDocs = comments.stream().map(o -> {
                CommentDoc commentDoc = Convert.convert(CommentDoc.class, o);
                commentDoc.setCommenter(o.getCreateUser());
                commentDoc.setCommentTime(o.getCreateTime());
                return commentDoc;
            }).collect(Collectors.toList());

            esService.index(commentDocs);
        }
    }
}
