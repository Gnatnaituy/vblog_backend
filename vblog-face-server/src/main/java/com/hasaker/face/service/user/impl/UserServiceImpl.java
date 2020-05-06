package com.hasaker.face.service.user.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.FriendDoc;
import com.hasaker.account.document.FriendRequestDoc;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.PageInfo;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.component.oss.service.UploadService;
import com.hasaker.face.exception.enums.UserExceptionEnums;
import com.hasaker.face.service.post.PostService;
import com.hasaker.face.service.post.TopicService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.request.RequestUserSearchVo;
import com.hasaker.face.vo.response.ResponseTopicInfoVo;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.PostDoc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/3 18:09
 * @description UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PostService postService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private EsService esService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * Search or list users
     * @param searchVo
     * @return
     */
    @Override
    public PageInfo<ResponseUserInfoVo> search(RequestUserSearchVo searchVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchVo);

        // Search by keyword is keyword is not null else match all
        String keyword = searchVo.getKeyword();
        QueryBuilder queryBuilder = ObjectUtils.isNull(keyword)
                ? QueryBuilders.matchAllQuery()
                : new BoolQueryBuilder()
                .should(QueryBuilders.termQuery(UserDoc.USERNAME, keyword))
                .should(QueryBuilders.termQuery(UserDoc.EMAIL, keyword))
                .should(QueryBuilders.termQuery(UserDoc.PHONE, keyword))
                .should(QueryBuilders.termQuery(UserDoc.COUNTRY, keyword))
                .should(QueryBuilders.termQuery(UserDoc.PROVINCE, keyword))
                .should(QueryBuilders.termQuery(UserDoc.CITY, keyword))
                .should(QueryBuilders.matchQuery(keyword, UserDoc.BIO));
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        // Configuration page
        searchQuery.setPageable(PageRequest.of(searchVo.getStart(), searchVo.getSize()));

        // If keyword is null, sort by register time
        if (ObjectUtils.isNull(searchVo.getKeyword())) {
            searchQuery.addSort(Sort.by(Sort.Order.desc(UserDoc.REGISTER_TIME)));
        }

        Page<UserDoc> userDocs = esService.page(searchQuery, UserDoc.class);

        // Convert Page to PageInfo
        PageInfo<ResponseUserInfoVo> page = new PageInfo<>(userDocs);
        if (ObjectUtils.isNotNull(userDocs.getContent())) {
            page.setContent(userDocs.getContent().stream()
                    .map(o -> Convert.convert(ResponseUserInfoVo.class, o))
                    .collect(Collectors.toList()));
        }

        return page;
    }

    /**
     * Obtain user detail information by user ID
     * @param userId
     * @return
     */
    @Override
    public ResponseUserDetailVo detail(Long userId, Long loggedUserId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        UserDoc userDoc = esService.getById(userId, UserDoc.class);
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(userDoc);

        // User's personal information
        ResponseUserDetailVo userDetailVo = Convert.convert(ResponseUserDetailVo.class, userDoc);
        userDetailVo.setAvatar(uploadService.generateAccessUrl(userDetailVo.getAvatar()));
        userDetailVo.setBackground(uploadService.generateAccessUrl(userDetailVo.getBackground()));
        if (ObjectUtils.isNotNull(userDoc.getBlocks())) {
            List<UserDoc> blockedUserDocs = esService.getByIds(userDoc.getBlocks(), UserDoc.class);
            userDetailVo.setBlocks(blockedUserDocs.stream()
                    .map(o -> Convert.convert(ResponseUserInfoVo.class, o))
                    .collect(Collectors.toList()));
            userDetailVo.getBlocks().forEach(o -> o.setAvatar(uploadService.generateAccessUrl(o.getAvatar())));
        } else {
            userDetailVo.setBlocks(Collections.emptyList());
        }

        // User's topics
        userDetailVo.setTopics(topicService.getUserTopics(userId));
        // User's words
        userDetailVo.setWords(postService.getUserWords(userId));

        if (ObjectUtils.isNotNull(loggedUserId) && !userId.equals(loggedUserId)) {
            // Friend status
            List<FriendDoc> friendDocs = esService.list(new Pair<>(Consts.USER_ID, loggedUserId), FriendDoc.class);
            if (ObjectUtils.isNotNull(friendDocs)) {
                Optional<FriendDoc> friend = friendDocs.stream().filter(o -> o.getFriendId().equals(userId)).findAny();
                if (friend.isPresent()) {
                    userDetailVo.setFriendStatus(Consts.IS_FRIEND);
                }
            } else {
                List<FriendRequestDoc> friendRequestDocs = esService.list(Arrays.asList(
                        new Pair<>(FriendRequestDoc.SENDER_ID, loggedUserId),
                        new Pair<>(FriendRequestDoc.RECEIVER_ID, userId)), FriendRequestDoc.class);
                if (ObjectUtils.isNotNull(friendRequestDocs)) {
                    FriendRequestDoc friendRequestDoc = friendRequestDocs.get(0);
                    if (friendRequestDoc.getRequestStatus().equals("FRIEND_REQUEST_STATUS_003")) {
                        userDetailVo.setFriendStatus(Consts.REQUEST_DENIED);
                    } else {
                        userDetailVo.setFriendStatus(Consts.REQUEST_SEND);
                    }
                } else {
                    userDetailVo.setFriendStatus(Consts.NOT_FRIEND);
                }
            }

            // Block status
            UserDoc loggedUserDoc = esService.getById(loggedUserId, UserDoc.class);
            if (ObjectUtils.isNotNull(loggedUserDoc.getBlocks())) {
                userDetailVo.setBlocked(loggedUserDoc.getBlocks().contains(userId));
            } else {
                userDetailVo.setBlocked(false);
            }
        }

        return userDetailVo;
    }

    /**
     * Obtain userId -> userInfo map by userIds
     * @param userIds
     * @return
     */
    @Override
    public Map<Long, ResponseUserInfoVo> mapUserInfo(Collection<Long> userIds) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userIds);

        List<UserDoc> userDocs = esService.list(QueryBuilders.termsQuery(Consts.ID, userIds), UserDoc.class);

        return userDocs.stream().collect(Collectors.toMap(UserDoc::getId, o -> {
            ResponseUserInfoVo userInfoVo = Convert.convert(ResponseUserInfoVo.class, o);
            userInfoVo.setAvatar(uploadService.generateAccessUrl(userInfoVo.getAvatar()));
            return userInfoVo; }, (o1, o2) -> o2));
    }

    /**
     * Obtain userInfo list by userIds
     * @param userIds
     * @return
     */
    @Override
    public List<ResponseUserInfoVo> listUserInfo(Collection<Long> userIds) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userIds);

        List<UserDoc> userDocs = esService.list(QueryBuilders.termsQuery(Consts.ID, userIds), UserDoc.class);

        return userDocs.stream().map(o -> Convert.convert(ResponseUserInfoVo.class, o)).collect(Collectors.toList());
    }

    /**
     * Recommend a user for current logged user
     * Recommend by user's topics and words
     * @param userId
     * @return
     */
    @Override
    public ResponseUserDetailVo recommendUser(Long userId) {

        // Obtain topics and words for currentUser
        List<Long> topics = topicService.getUserTopics(userId).stream()
                .map(ResponseTopicInfoVo::getId).collect(Collectors.toList());
        List<String> words = postService.getUserWords(userId);
        if (ObjectUtils.isEmpty(topics) && ObjectUtils.isEmpty(words)) {
            return null;
        }

        // Filter posts by topics and/or words
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (ObjectUtils.isNotEmpty(topics)) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termsQuery(PostDoc.TOPICS, topics));
        }
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.mustNot(QueryBuilders.termQuery(PostDoc.POSTER, userId));
        if (ObjectUtils.isNotEmpty(words)) {
            words.forEach(o -> boolQueryBuilder.should(QueryBuilders.matchQuery(PostDoc.CONTENT, o)));
            nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        }
        List<PostDoc> postDocs = esService.list(nativeSearchQueryBuilder.build(), PostDoc.class);
        if (ObjectUtils.isEmpty(postDocs)) {
            return null;
        }

        // Statistics user and score
        Map<Long, Integer> userScore = new HashMap<>();
        Integer score = userScore.size();
        for (PostDoc postDoc : postDocs) {
            if (userScore.containsKey(postDoc.getPoster())) {
                userScore.put(postDoc.getPoster(), userScore.get(postDoc.getPoster()) + score);
            } else {
                userScore.put(postDoc.getPoster(), score);
            }
            score--;
        }

        // Obtain the max scored user
        Long recommendUser = -1L;
        Integer maxScore = -1;
        for (Map.Entry<Long, Integer> entry : userScore.entrySet()) {
            if (entry.getValue() > maxScore) {
                recommendUser = entry.getKey();
            }
        }

        return detail(recommendUser, userId);
    }
}
