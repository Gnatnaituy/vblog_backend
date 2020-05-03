package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.service.post.CommentService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.response.ResponsePostCommentVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.face.vo.response.message.ResponseMessageCommentVo;
import com.hasaker.post.document.CommentDoc;
import com.hasaker.post.message.CommentMessageDoc;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

        List<CommentDoc> commentDocs = esService.list(QueryBuilders.termQuery(Consts.POST_ID, postId), CommentDoc.class);
        if (ObjectUtils.isNull(commentDocs)) {
            return Collections.emptyList();
        }

        // Obtain All commenter's information
        List<Long> commenters = commentDocs.stream().map(CommentDoc::getCommenter).distinct().collect(Collectors.toList());
        Map<Long, ResponseUserInfoVo> userInfoMap = userService.mapUserInfo(commenters);
        Map<Long, CommentDoc> commentDocMap = commentDocs.stream().collect(Collectors.toMap(CommentDoc::getId, o -> o));

        return commentDocs.stream().filter(o -> !Consts.YES.equals(o.getDeleted())).map(o -> {
            ResponsePostCommentVo commentVo = Convert.convert(ResponsePostCommentVo.class, o);
            commentVo.setCommenter(userInfoMap.get(o.getCommenter()));
            if (ObjectUtils.isNotNull(o.getCommentId())) {
                commentVo.setOriginCommenter(userInfoMap.get(commentDocMap.get(o.getCommentId()).getCommenter()));
            }
            return commentVo;
        }).sorted((o1, o2) -> (int) (o2.getCommentTime() - o1.getCommentTime())).collect(Collectors.toList());
    }

    /**
     * Get comment by ID
     * @param commentId
     * @return
     */
    @Override
    public ResponsePostCommentVo getById(Long commentId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(commentId);

        CommentDoc commentDoc = esService.getById(commentId, CommentDoc.class);
        ResponsePostCommentVo commentVo = Convert.convert(ResponsePostCommentVo.class, commentDoc);

        UserDoc commenter = esService.getById(commentDoc.getCommenter(), UserDoc.class);
        ResponseUserInfoVo commenterVo = Convert.convert(ResponseUserInfoVo.class, commenter);
        commentVo.setCommenter(commenterVo);

        if (ObjectUtils.isNotNull(commentDoc.getCommentId())) {
            UserDoc originCommenter = esService.getById(commentDoc.getCommenter(), UserDoc.class);
            ResponseUserInfoVo originCommenterVo = Convert.convert(ResponseUserInfoVo.class, originCommenter);
            commentVo.setOriginCommenter(originCommenterVo);
        }

        return commentVo;
    }

    /**
     * List unread comment message for current logged user
     * @param userId
     * @return
     */
    @Override
    public List<ResponseMessageCommentVo> listMessage(Long userId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        List<Pair<String, Object>> matchFields = new ArrayList<>();
        matchFields.add(new Pair<>(CommentMessageDoc.RECEIVER, userId));
        matchFields.add(new Pair<>(CommentMessageDoc.STATUS, Consts.MESSAGE_STATUS_UNREAD));
        List<CommentMessageDoc> commentMessageDocs = esService.list(matchFields, CommentMessageDoc.class);

        if (ObjectUtils.isNotNull(commentMessageDocs)) {
            List<Long> commenterIds = commentMessageDocs.stream().map(CommentMessageDoc::getCreateUser).collect(Collectors.toList());
            List<UserDoc> commenters = esService.getByIds(commenterIds, UserDoc.class);
            Map<Long, ResponseUserInfoVo> userMap = commenters.stream()
                    .map(o -> Convert.convert(ResponseUserInfoVo.class, o))
                    .collect(Collectors.toMap(ResponseUserInfoVo::getId, o -> o));

            return commentMessageDocs.stream().map(o -> {
                ResponseMessageCommentVo commentVo = Convert.convert(ResponseMessageCommentVo.class, o);
                commentVo.setCreateUser(userMap.get(o.getCreateUser()));
                return commentVo;
            }).sorted((o1, o2) -> (int) (o2.getCreateTime() - o1.getCreateTime())).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
