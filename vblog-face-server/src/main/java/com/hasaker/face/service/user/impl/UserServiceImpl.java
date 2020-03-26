package com.hasaker.face.service.user.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.PageInfo;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.exception.enums.UserExceptionEnums;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.request.RequestUserSearchVo;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * Search users
     * @param searchVo
     * @return
     */
    @Override
    public PageInfo<ResponseUserInfoVo> search(RequestUserSearchVo searchVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchVo);

        String keyword = searchVo.getKeyword();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(QueryBuilders.termQuery(UserDoc.USERNAME, keyword));
        boolQueryBuilder.should(QueryBuilders.termQuery(UserDoc.EMAIL, keyword));
        boolQueryBuilder.should(QueryBuilders.termQuery(UserDoc.PHONE, keyword));
        boolQueryBuilder.should(QueryBuilders.termQuery(UserDoc.COUNTRY, keyword));
        boolQueryBuilder.should(QueryBuilders.termQuery(UserDoc.PROVINCE, keyword));
        boolQueryBuilder.should(QueryBuilders.termQuery(UserDoc.CITY, keyword));
        boolQueryBuilder.should(QueryBuilders.matchQuery(keyword, UserDoc.BIO));

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();

        // Configuration page
        searchQuery.setPageable(PageRequest.of(searchVo.getStart(), searchVo.getSize()));

        Page<UserDoc> userDocs = esService.page(searchQuery, UserDoc.class);

        PageInfo<ResponseUserInfoVo> page = new PageInfo<>(userDocs);
        page.setContent(userDocs.getContent().stream()
                .map(o -> Convert.convert(ResponseUserInfoVo.class, o))
                .collect(Collectors.toList()));

        return page;
    }

    /**
     * Obtain user detail information by user ID
     * @param userId
     * @return
     */
    @Override
    public ResponseUserDetailVo detail(String userId) {
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
}
