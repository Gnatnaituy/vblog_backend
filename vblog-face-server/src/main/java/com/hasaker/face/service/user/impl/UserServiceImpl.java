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
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.request.RequestUserSearchVo;
import com.hasaker.face.vo.response.ResponseTopicInfoVo;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.PostDoc;
import com.hasaker.post.document.TopicDoc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        }

        // User's topics
        List<TopicDoc> topicDocs = esService.list(new Pair<>(TopicDoc.CREATE_USER, userId), TopicDoc.class);
        if (ObjectUtils.isNotNull(topicDocs)) {
            userDetailVo.setTopics(topicDocs.stream()
                    .map(o -> Convert.convert(ResponseTopicInfoVo.class, o))
                    .collect(Collectors.toList()));
        }

        // User's words
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery(PostDoc.POSTER, userId));
        queryBuilder.addAggregation(AggregationBuilders.terms("stringFieldAgg").field(PostDoc.CONTENT).size(10));
        AggregatedPage<PostDoc> res = (AggregatedPage<PostDoc>)
                elasticsearchOperations.queryForPage(queryBuilder.build(), PostDoc.class);
        Aggregations aggregations = res.getAggregations();
        ParsedStringTerms stringTerms = aggregations.get("stringFieldAgg");
        List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) stringTerms.getBuckets();
        if (ObjectUtils.isNotNull(buckets)) {
            userDetailVo.setWords(buckets.stream()
                    .sorted((o1, o2) -> (int) (o2.getDocCount() - o1.getDocCount()))
                    .map(o -> o.getKey().toString()).collect(Collectors.toList()));
        }

        if (ObjectUtils.isNotNull(loggedUserId) && !userId.equals(loggedUserId)) {
            // Friend status
            List<FriendDoc> friendDocs = esService.list(new Pair<>(Consts.USER_ID, loggedUserId), FriendDoc.class);
            if (ObjectUtils.isNotNull(friendDocs)) {
                Optional<FriendDoc> friend = friendDocs.stream().filter(o -> o.getFriendId().equals(userId)).findAny();
                if (friend.isPresent()) {
                    userDetailVo.setFriendStatus(Consts.IS_FRIEND);
                }
            } else {
                List<FriendRequestDoc> friendRequestDocs = esService.list(
                        new Pair<>(FriendRequestDoc.SENDER_ID, loggedUserId), FriendRequestDoc.class);
                if (ObjectUtils.isNotNull(friendRequestDocs)) {
                    Optional<FriendRequestDoc> friendRequest = friendRequestDocs.stream()
                            .filter(o -> o.getSenderId().equals(loggedUserId))
                            .findAny();
                    if (friendRequest.isPresent()) {
                        if (friendRequest.get().getRequestStatus().equals("FRIEND_REQUEST_STATUS_003")) {
                            userDetailVo.setFriendStatus(Consts.REQUEST_DENIED);
                        } else {
                            userDetailVo.setFriendStatus(Consts.REQUEST_SEND);
                        }
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
}
