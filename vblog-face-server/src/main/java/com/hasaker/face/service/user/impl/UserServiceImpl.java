package com.hasaker.face.service.user.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.PageInfo;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.exception.enums.UserExceptionEnums;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.request.RequestUserSearchVo;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    private EsService esService;

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
    public ResponseUserDetailVo detail(Long userId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        UserDoc userDoc = esService.getById(userId, UserDoc.class);
        UserExceptionEnums.USER_NOT_EXISTS.assertNotEmpty(userDoc);

        ResponseUserDetailVo userDetailVo = Convert.convert(ResponseUserDetailVo.class, userDoc);
        if (ObjectUtils.isNotNull(userDoc.getBlocks())) {
            List<UserDoc> blockedUserDocs = esService.getByIds(userDoc.getBlocks(), UserDoc.class);
            userDetailVo.setBlocks(blockedUserDocs.stream()
                    .map(o -> Convert.convert(ResponseUserInfoVo.class, o))
                    .collect(Collectors.toList()));
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

        return userDocs.stream().collect(Collectors.toMap(UserDoc::getId,
                o -> Convert.convert(ResponseUserInfoVo.class, o), (o1, o2) -> o2));
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
