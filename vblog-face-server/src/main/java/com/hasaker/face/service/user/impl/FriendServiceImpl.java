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
import com.hasaker.face.service.user.FriendService;
import com.hasaker.face.vo.request.SearchVo;
import com.hasaker.face.vo.response.ResponseFriendInfoVo;
import com.hasaker.face.vo.response.ResponseFriendRequestVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/26 16:26
 * @description FriendServiceImpl
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private EsService esService;

    /**
     * List friends by userId
     * @param searchVo
     * @return
     */
    @Override
    public PageInfo<ResponseFriendInfoVo> list(SearchVo searchVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchVo);

        Page<FriendDoc> friendDocs = esService.page(new Pair<>(Consts.USER_ID, searchVo.getUserId()), FriendDoc.class);
        if (ObjectUtils.isNotNull(friendDocs.getContent())) {
            List<Long> friendIds = friendDocs.stream().map(FriendDoc::getFriendId).collect(Collectors.toList());
            List<UserDoc> userDocs = esService.getByIds(friendIds, UserDoc.class);
            PageInfo<ResponseFriendInfoVo> friendPage = new PageInfo<>(friendDocs);
            Map<Long, FriendDoc> friendDocMap = new HashMap<>(userDocs.size());
            friendDocs.getContent().forEach(o -> friendDocMap.put(o.getFriendId(), o));
            friendPage.setContent(userDocs.stream().map(user -> {
                ResponseFriendInfoVo friendInfoVo = Convert.convert(ResponseFriendInfoVo.class, user);
                friendInfoVo.setAvatar(uploadService.generateAccessUrl(friendInfoVo.getAvatar()));
                friendInfoVo.setRemark(friendDocMap.get(user.getId()).getRemark());
                friendInfoVo.setVisibility(friendDocMap.get(user.getId()).getVisibility());
                return friendInfoVo;
            }).collect(Collectors.toList()));

            return friendPage;
        }

        return new PageInfo<>();
    }

    /**
     * List friend requests for current logged user
     * @param userId
     * @return
     */
    @Override
    public List<ResponseFriendRequestVo> listFriendRequest(Long userId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery(FriendRequestDoc.RECEIVER_ID, userId))
                .should(QueryBuilders.termQuery(FriendRequestDoc.SENDER_ID, userId));
        List<FriendRequestDoc> friendRequestDocs = esService.list(nativeSearchQueryBuilder
                .withQuery(boolQueryBuilder).build(),FriendRequestDoc.class);
        if (ObjectUtils.isNotNull(friendRequestDocs)) {
            Set<Long> userIds = new HashSet<>();
            friendRequestDocs.forEach(o -> {
                userIds.add(o.getSenderId());
                userIds.add(o.getReceiverId());
            });
            List<UserDoc> userDocs = esService.getByIds(userIds, UserDoc.class);
            Map<Long, ResponseUserInfoVo> userMap = userDocs.stream().map(o -> {
                ResponseUserInfoVo userInfoVo = Convert.convert(ResponseUserInfoVo.class, o);
                userInfoVo.setAvatar(uploadService.generateAccessUrl(o.getAvatar()));
                return userInfoVo;
            }).collect(Collectors.toMap(ResponseUserInfoVo::getId, o -> o, (o1, o2) -> o2));

            return friendRequestDocs.stream()
                    .filter(o -> !o.getRequestStatus().equals("FRIEND_REQUEST_STATUS_004"))
                    .filter(o -> !(o.getRequestStatus().equals("FRIEND_REQUEST_STATUS_001") && o.getSenderId().equals(userId)))
                    .map(o -> {
                        ResponseFriendRequestVo requestVo = Convert.convert(ResponseFriendRequestVo.class, o);
                        requestVo.setSender(userMap.get(o.getSenderId()));
                        requestVo.setReceiver(userMap.get(o.getReceiverId()));
                        return requestVo;
                    }).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
