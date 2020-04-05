package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.consts.RequestConsts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.PageInfo;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.component.redis.service.RedisService;
import com.hasaker.face.service.post.CommentService;
import com.hasaker.face.service.post.PostService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.request.RequestPostSearchVo;
import com.hasaker.face.vo.response.ResponsePostImageVo;
import com.hasaker.face.vo.response.ResponsePostTopicVo;
import com.hasaker.face.vo.response.ResponsePostVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.ImageDoc;
import com.hasaker.post.document.PostDoc;
import com.hasaker.post.document.TopicDoc;
import com.hasaker.post.document.VoteDoc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/4 11:24
 * @description PostServiceImpl
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EsService esService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private HttpServletRequest request;

    /**
     * Search or list posts
     * @param pageVo
     * @return
     */
    @Override
    public PageInfo<ResponsePostVo> page(RequestPostSearchVo pageVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(pageVo);

        // Search by keyword is keyword is not null else match all
        String keyword = pageVo.getKeyword();
        QueryBuilder queryBuilder = ObjectUtils.isNull(keyword)
                ? QueryBuilders.matchAllQuery()
                : new BoolQueryBuilder()
                .should(QueryBuilders.matchQuery(PostDoc.CONTENT, keyword))
                .should(QueryBuilders.termQuery(PostDoc.TOPICS, keyword));
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        // Configuration page
        searchQuery.setPageable(PageRequest.of(pageVo.getStart(), pageVo.getSize()));

        // If keyword is null, sort by create time of user
        if (ObjectUtils.isNull(pageVo.getKeyword())) {
            searchQuery.addSort(Sort.by(Sort.Order.desc(PostDoc.POST_TIME)));
        }

        // Obtain comments and votes for those posts
        Page<PostDoc> postDocPage = esService.page(searchQuery, PostDoc.class);
        PageInfo<ResponsePostVo> pageInfo = new PageInfo<>(postDocPage);
        if (ObjectUtils.isNotNull(postDocPage.getContent())) {
            // Obtain topic in this posts
            Set<Long> topicIds = new HashSet<>();
            postDocPage.getContent().forEach(o -> topicIds.addAll(o.getTopics()));
            List<TopicDoc> topicDocs = esService.getByIds(topicIds, TopicDoc.class);
            Map<Long, ResponsePostTopicVo> topicMap = topicDocs.stream().collect(
                    Collectors.toMap(TopicDoc::getId, o -> Convert.convert(ResponsePostTopicVo.class, o)));

            // Obtains users' info in those posts
            Map<Long, ResponseUserInfoVo> posterMap = userService.mapUserInfo(postDocPage.getContent().stream()
                    .map(PostDoc::getPoster).collect(Collectors.toList()));
            List<ResponsePostVo> postVos = postDocPage.getContent().stream().map(o -> {
                ResponsePostVo postVo = Convert.convert(ResponsePostVo.class, o);
                postVo.setPoster(posterMap.get(o.getPoster()));
                postVo.setTopics(o.getTopics().stream().map(topicMap::get).collect(Collectors.toList()));
                return postVo;
            }).collect(Collectors.toList());

            // Fill comments
            postVos.forEach(o -> o.setComments(commentService.listByPostId(o.getId())));

            // Fill images and votes to posts
            fillImages(postVos);
            fillVotes(postVos);
            pageInfo.setContent(postVos);
        }

        return pageInfo;
    }


    /**
     * Obtain and set images for posts
     * @param postVos
     */
    private void fillImages(List<ResponsePostVo> postVos) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postVos);

        List<Long> postIds = postVos.stream().map(ResponsePostVo::getId).collect(Collectors.toList());
        List<ImageDoc> imageDocs = esService.list(QueryBuilders.termsQuery(Consts.POST_ID, postIds), ImageDoc.class);
        if (ObjectUtils.isNotNull(imageDocs)) {
            Map<Long, List<ResponsePostImageVo>> imageMap = imageDocs.stream()
                    .collect(Collectors.groupingBy(ImageDoc::getPostId, Collectors.mapping(
                            o -> Convert.convert(ResponsePostImageVo.class, o), Collectors.toList())));
            postVos.forEach(o -> o.setImages(imageMap.get(o.getId())));
            postVos.forEach(o -> o.getImages().sort((o1, o2) -> o1.getSort() - o2.getSort()));
        } else {
            postVos.forEach(o -> o.setImages(Collections.emptyList()));
        }
    }

    /**
     * Obtain and set votes for posts
     * @param postVos
     */
    private void fillVotes(List<ResponsePostVo> postVos) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postVos);

        Long currentUserId = getUserId();

        List<Long> postIds = postVos.stream().map(ResponsePostVo::getId).collect(Collectors.toList());
        List<VoteDoc> voteDocs = esService.list(QueryBuilders.termsQuery(Consts.POST_ID, postIds), VoteDoc.class);
        // Posts voted by current user in search result
        List<Long> votedPostIds = voteDocs.stream().filter(o -> o.getVoter().equals(currentUserId))
                .filter(o -> ObjectUtils.isNotNull(o.getPostId()) && ObjectUtils.isNull(o.getCommentId()))
                .distinct().map(VoteDoc::getPostId).collect(Collectors.toList());
        if (ObjectUtils.isNotNull(voteDocs)) {
            Set<Long> voters = voteDocs.stream().map(VoteDoc::getVoter).collect(Collectors.toSet());
            Map<Long, ResponseUserInfoVo> userMap = userService.mapUserInfo(voters);
            Map<Long, List<VoteDoc>> voteMap = voteDocs.stream().collect(Collectors.groupingBy(VoteDoc::getPostId));
            postVos.forEach(o -> {
                if (ObjectUtils.isNotNull(voteMap.get(o.getId()))) {
                    o.setVoters(voteMap.get(o.getId()).stream().map(x -> userMap.get(x.getVoter())).collect(Collectors.toList()));
                    o.setVoteByMe(votedPostIds.contains(o.getId()));
                } else {
                    o.setVoters(Collections.emptyList());
                }
            });
        } else {
            postVos.forEach(o -> o.setVoters(Collections.emptyList()));
        }
    }

    /**
     * Get current user's ID from tokenStore by JWT Token
     * @return
     */
    private Long getUserId() {
        String bearerToken = request.getHeader(RequestConsts.AUTHORIZATION);
        if (ObjectUtils.isNotNull(bearerToken)) {
            bearerToken = bearerToken.split(" ")[1];
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(bearerToken);
            if (ObjectUtils.isNotNull(accessToken)) {
                return Long.valueOf(accessToken.getAdditionalInformation().get(Consts.USER_ID).toString());
            }
        }

        // There will not be a user with id -1
        return -1L;
    }
}
