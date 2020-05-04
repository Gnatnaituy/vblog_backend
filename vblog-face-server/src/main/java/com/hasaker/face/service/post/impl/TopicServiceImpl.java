package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.component.oss.service.UploadService;
import com.hasaker.face.service.post.TopicService;
import com.hasaker.face.vo.response.ResponseTopicDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.PostDoc;
import com.hasaker.post.document.TopicDoc;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/5/3 02:50
 * @description TopicServiceImpl
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private EsService esService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * Obtain topic detail by topicId
     * @param topicId
     * @return
     */
    @Override
    public ResponseTopicDetailVo detail(Long topicId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(topicId);

        TopicDoc topicDoc = esService.getById(topicId, TopicDoc.class);
        UserDoc userDoc = esService.getById(topicDoc.getCreateUser(), UserDoc.class);

        ResponseTopicDetailVo topicDetailVo = Convert.convert(ResponseTopicDetailVo.class, topicDoc);
        topicDetailVo.setBackground(uploadService.generateAccessUrl(topicDetailVo.getBackground()));
        topicDetailVo.setCreateUser(Convert.convert(ResponseUserInfoVo.class, userDoc));

        // Analyze most active users for this topic
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withFilter(QueryBuilders.termQuery(PostDoc.TOPICS, topicId));
        queryBuilder.addAggregation(AggregationBuilders.terms("longFieldAgg").field(PostDoc.POSTER).size(10));
        AggregatedPage<PostDoc> res = (AggregatedPage<PostDoc>) elasticsearchOperations.queryForPage(queryBuilder.build(), PostDoc.class);
        ParsedLongTerms longTerms = res.getAggregations().get("longFieldAgg");
        List<ParsedLongTerms.ParsedBucket> buckets = (List<ParsedLongTerms.ParsedBucket>) longTerms.getBuckets();

        Set<Long> userIds = buckets.stream().map(o -> Long.valueOf(o.getKey().toString())).collect(Collectors.toSet());
        List<UserDoc> userDocs = esService.getByIds(userIds, UserDoc.class);
        topicDetailVo.setActiveUsers(userDocs.stream()
                .map(o -> Convert.convert(ResponseUserInfoVo.class, userDoc))
                .distinct().collect(Collectors.toList()));
        topicDetailVo.getActiveUsers().forEach(o -> o.setAvatar(uploadService.generateAccessUrl(o.getAvatar())));

        return topicDetailVo;
    }
}
