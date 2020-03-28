package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.service.post.CommentService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.response.ResponsePostCommentVo;
import com.hasaker.face.vo.response.ResponsePostVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.CommentDoc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/28 02:22
 * @description CommentServiceImpl
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    private EsService esService;

    /**
     * List comments by postId
     * @param postId
     * @return
     */
    @Override
    public List<ResponsePostCommentVo> listByPostId(Long postId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postId);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery(Consts.POST_ID, postId));
        List<CommentDoc> commentDocs = esService.list(boolQueryBuilder, CommentDoc.class);

        return generateCommentTree(commentDocs);
    }

    /**
     * List comments by commentId
     * @param commentId
     * @return
     */
    @Override
    public List<ResponsePostCommentVo> listByCommentId(Long commentId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(commentId);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery(Consts.COMMENT_ID, commentId));
        List<CommentDoc> commentDocs = esService.list(boolQueryBuilder, CommentDoc.class);

        return generateCommentTree(commentDocs);
    }

    /**
     * Generate comment tree recursively
     * @param commentDocs
     */
    @Override
    public List<ResponsePostCommentVo> generateCommentTree(List<CommentDoc> commentDocs) {
        if (ObjectUtils.isNull(commentDocs)) {
            return new ArrayList<>();
        }

        Map<Long, ResponseUserInfoVo> commenterMap = userService.mapUserInfo(commentDocs.stream()
                .map(CommentDoc::getCommenter).collect(Collectors.toList()));

        List<ResponsePostCommentVo> commentVos = commentDocs.stream().map(o -> {
            ResponsePostCommentVo commentVo = Convert.convert(ResponsePostCommentVo.class, o);
            commentVo.setCommenter(commenterMap.get(o.getCommenter()));
            return commentVo;
        }).collect(Collectors.toList());

        commentVos.forEach(o -> {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery(Consts.COMMENT_ID, o.getId()));
            List<CommentDoc> replies = esService.list(boolQueryBuilder, CommentDoc.class);
            o.setReplies(generateCommentTree(replies));
        });

        return commentVos;
    }

    /**
     * Fill comments to post
     * @param postVos
     */
    private void fillComments(List<ResponsePostVo> postVos) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postVos);

        List<Long> postIds = postVos.stream().map(ResponsePostVo::getId).collect(Collectors.toList());
        List<CommentDoc> commentDocs = esService.list(QueryBuilders.termsQuery(Consts.POST_ID, postIds), CommentDoc.class);
        if (ObjectUtils.isNotNull(commentDocs)) {
            List<ResponsePostCommentVo> commentVos = commentDocs.stream()
                    .map(o -> Convert.convert(ResponsePostCommentVo.class, o)).collect(Collectors.toList());
            fillCommentReplies(commentVos);
            Map<Long, List<ResponsePostCommentVo>> commentMap = commentVos.stream()
                    .collect(Collectors.groupingBy(ResponsePostCommentVo::getPostId));
            postVos.forEach(o -> o.setComments(commentMap.get(o.getId())));
        }
    }

    /**
     * Generate comment tree recursively
     * @param commentVos
     */
    private void fillCommentReplies(List<ResponsePostCommentVo> commentVos) {
        if (ObjectUtils.isNull(commentVos)) {
            return;
        }

        List<Long> commentIds = commentVos.stream().map(ResponsePostCommentVo::getId).collect(Collectors.toList());
        List<CommentDoc> replies = esService.list(QueryBuilders.termsQuery(Consts.COMMENT_ID, commentIds), CommentDoc.class);
        if (ObjectUtils.isNotNull(replies)) {
            Map<Long, List<ResponsePostCommentVo>> repliesMap = replies.stream().collect(
                    Collectors.groupingBy(CommentDoc::getCommentId,
                            Collectors.mapping(o -> Convert.convert(ResponsePostCommentVo.class, o), Collectors.toList())));
            commentVos.forEach(o -> o.setReplies(repliesMap.get(o.getId())));
            commentVos.forEach(o -> fillCommentReplies(o.getReplies()));
        }
    }
}
