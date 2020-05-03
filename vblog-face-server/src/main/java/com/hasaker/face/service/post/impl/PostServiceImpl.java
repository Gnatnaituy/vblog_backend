package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.consts.RequestConsts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.PageInfo;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.component.oss.service.UploadService;
import com.hasaker.face.exception.enums.PostExceptionEnums;
import com.hasaker.face.service.post.CommentService;
import com.hasaker.face.service.post.PostService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.request.RequestAggregationVo;
import com.hasaker.face.vo.request.RequestMessageReadVo;
import com.hasaker.face.vo.request.RequestPostSearchVo;
import com.hasaker.face.vo.response.*;
import com.hasaker.post.document.*;
import com.hasaker.post.feign.PostClient;
import com.hasaker.post.message.CommentMessageDoc;
import com.hasaker.post.message.VoteMessageDoc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
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
    private PostClient postClient;
    @Autowired
    private EsService esService;
    @Autowired
    private UploadService uploadService;
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

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (ObjectUtils.isNotNull(pageVo.getKeyword())) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(PostDoc.CONTENT, pageVo.getKeyword()));
        }
        if (ObjectUtils.isNotNull(pageVo.getPoster())) {
            boolQueryBuilder.must(QueryBuilders.termQuery(PostDoc.POSTER, pageVo.getPoster()));
        }
        if (ObjectUtils.isNotNull(pageVo.getTopic())) {
            boolQueryBuilder.must(QueryBuilders.termQuery(PostDoc.TOPICS, pageVo.getTopic()));
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();

        // Configuration page
        searchQuery.setPageable(PageRequest.of(pageVo.getStart(), pageVo.getSize()));

        // Sort by create time of user if keyword is empty
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
            List<TopicDoc> topicDocs = ObjectUtils.isNull(topicIds) ? null : esService.getByIds(topicIds, TopicDoc.class);
            Map<Long, ResponseTopicInfoVo> topicMap = ObjectUtils.isNull(topicDocs) ? new HashMap<>()
                    : topicDocs.stream().collect(Collectors.toMap(TopicDoc::getId, o -> Convert.convert(ResponseTopicInfoVo.class, o)));

            // Obtains users' info in those posts
            Map<Long, ResponseUserInfoVo> posterMap = userService.mapUserInfo(postDocPage.getContent().stream()
                    .map(PostDoc::getPoster).collect(Collectors.toList()));
            List<ResponsePostVo> postVos = postDocPage.getContent().stream().map(o -> {
                ResponsePostVo postVo = Convert.convert(ResponsePostVo.class, o);
                postVo.setPoster(posterMap.get(o.getPoster()));
                postVo.setTopics(ObjectUtils.isNotNull(o.getTopics())
                        ? o.getTopics().stream().map(topicMap::get).collect(Collectors.toList())
                        : Collections.emptyList());
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
     * Get post by ID
     * @param postId
     * @return
     */
    @Override
    public ResponsePostVo getById(Long postId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postId);

        PostDoc postDoc = esService.getById(postId, PostDoc.class);
        PostExceptionEnums.POST_NOT_EXISTS.assertNotEmpty(postDoc);

        ResponsePostVo postVo = Convert.convert(ResponsePostVo.class, postDoc);

        if (ObjectUtils.isNotNull(postDoc.getTopics())) {
            List<TopicDoc> topicDocs = esService.getByIds(postDoc.getTopics(), TopicDoc.class);
            postVo.setTopics(topicDocs.stream().map(o -> Convert.convert(ResponseTopicInfoVo.class, o))
                    .collect(Collectors.toList()));
        }

        List<ResponseUserInfoVo> poster = userService.listUserInfo(Collections.singleton(postDoc.getPoster()));
        if (ObjectUtils.isNotNull(poster)) {
            postVo.setPoster(poster.iterator().next());
        }

        postVo.setComments(commentService.listByPostId(postVo.getId()));
        fillImages(Collections.singletonList(postVo));
        fillVotes(Collections.singletonList(postVo));

        return postVo;
    }

    /**
     * List hot worlds and respective doc count
     * @param aggregationVo
     * @return
     */
    @Override
    public List<ResponseHotWordsAggVo> getHotWords(RequestAggregationVo aggregationVo) {

        Map<String, Long> wordCount = esService.aggregateStringField(aggregationVo.getField(), aggregationVo.getSize(), PostDoc.class);

        return wordCount.entrySet().stream()
                .map(o -> new ResponseHotWordsAggVo(o.getKey(), o.getValue()))
                .sorted((o1, o2) -> (int) (o2.getCount() - o1.getCount()))
                .collect(Collectors.toList());
    }

    /**
     * List hot topics and respective doc count
     * @param aggregationVo
     * @return
     */
    @Override
    public List<ResponseHotTopicsAggVo> getHotTopics(RequestAggregationVo aggregationVo) {
        Map<Long, Long> topicCount = esService.aggregateLongField(aggregationVo.getField(), aggregationVo.getSize(), PostDoc.class);

        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.termsQuery(Consts.ID, topicCount.keySet()));
        searchQuery.setPageable(PageRequest.of(0, aggregationVo.getSize()));
        List<TopicDoc> topicDocs = esService.list(searchQuery, TopicDoc.class);

        return topicDocs.stream().map(o -> {
            ResponseHotTopicsAggVo aggVo = new ResponseHotTopicsAggVo();
            aggVo.setTopic(Convert.convert(ResponseTopicInfoVo.class, o));
            aggVo.setCount(topicCount.get(o.getId()));
            return aggVo;
        }).sorted((o1, o2) -> (int) (o2.getCount() - o1.getCount())).collect(Collectors.toList());
    }

    /**
     * List the most active users
     * Analyzed by post & comment & vote count
     * @param aggregationVo
     * @return
     */
    @Override
    public List<ResponseHotUsersAggVo> getHotUsers(RequestAggregationVo aggregationVo) {
        Map<Long, Long> userPostCount = esService.aggregateLongField(PostDoc.POSTER, Integer.MAX_VALUE, PostDoc.class);
        Map<Long, Long> userCommentCount = esService.aggregateLongField(CommentDoc.COMMENTER, Integer.MAX_VALUE, CommentDoc.class);
        Map<Long, Long> userVoteCount = esService.aggregateLongField(VoteDoc.VOTER, Integer.MAX_VALUE, VoteDoc.class);

        Set<Long> userIds = new HashSet<>();
        userIds.addAll(userPostCount.keySet());
        userIds.addAll(userCommentCount.keySet());
        userIds.addAll(userVoteCount.keySet());

        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.termsQuery(Consts.ID, userIds));
        searchQuery.setPageable(PageRequest.of(0, userIds.size()));
        List<UserDoc> userDocs = esService.list(searchQuery, UserDoc.class);

        return userDocs.stream().map(o -> {
            ResponseHotUsersAggVo aggVo = new ResponseHotUsersAggVo();
            aggVo.setUser(Convert.convert(ResponseUserInfoVo.class, o));
            aggVo.setCount(userPostCount.getOrDefault(o.getId(), 0L) * 5
                    + userCommentCount.getOrDefault(o.getId(), 0L) * 2
                    + userVoteCount.getOrDefault(o.getId(), 0L));
            return aggVo;
        }).sorted((o1, o2) -> (int) (o2.getCount() - o1.getCount()))
                .limit(aggregationVo.getSize())
                .collect(Collectors.toList());
    }

    /**
     * Mark message read
     * @param readVo
     */
    @Override
    public void readMessage(RequestMessageReadVo readVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(readVo);

        switch (readVo.getMessageType()) {
            case Consts.MESSATE_TYPE_COMMENT:
                esService.update(readVo.getMessageIds(), CommentMessageDoc.class,
                        new Pair<>(CommentMessageDoc.STATUS, Consts.MESSAGE_STATUS_READ));
            case Consts.MESSATE_TYPE_VOTE:
                esService.update(readVo.getMessageIds(), VoteMessageDoc.class,
                        new Pair<>(VoteMessageDoc.STATUS, Consts.MESSAGE_STATUS_READ));
        }
    }

    /**
     * Index all posts, comments, votes, topics to ES
     */
    @Override
    public void indexAll() {
        esService.deleteIndex(PostDoc.class);
        esService.deleteIndex(ImageDoc.class);
        esService.deleteIndex(CommentDoc.class);
        esService.deleteIndex(VoteDoc.class);
        esService.deleteIndex(TopicDoc.class);

        esService.createIndex(PostDoc.class);
        esService.createIndex(ImageDoc.class);
        esService.createIndex(CommentDoc.class);
        esService.createIndex(VoteDoc.class);
        esService.createIndex(TopicDoc.class);

        postClient.indexAllPosts();
        postClient.indexAllComments();
        postClient.indexAllVotes();
        postClient.indexAllTopics();
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
                    .collect(Collectors.groupingBy(ImageDoc::getPostId,
                            Collectors.mapping(o -> {
                                o.setUrl(uploadService.generateAccessUrl(o.getUrl()));
                                return Convert.convert(ResponsePostImageVo.class, o);
                            }, Collectors.toList())));
            postVos.forEach(o -> {
                if (ObjectUtils.isNotNull(imageMap.get(o.getId()))) {
                    o.setImages(imageMap.get(o.getId()));
                    o.getImages().sort(Comparator.comparingInt(ResponsePostImageVo::getSort));
                } else {
                    o.setImages(Collections.emptyList());
                }
            });
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
                    o.setVoteByMe(false);
                }
            });
        } else {
            postVos.forEach(o -> {
                o.setVoters(Collections.emptyList());
                o.setVoteByMe(false);
            });
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
