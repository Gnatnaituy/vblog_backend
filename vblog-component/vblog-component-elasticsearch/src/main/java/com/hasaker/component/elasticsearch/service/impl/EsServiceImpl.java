package com.hasaker.component.elasticsearch.service.impl;

import cn.hutool.core.lang.Pair;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public <T> List<T> list(SearchQuery searchQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchQuery);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        searchQuery.setPageable(PageRequest.of(0, 1000));

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public <T> List<T> list(QueryBuilder queryBuilder, Class<T> clazz) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        searchQuery.setPageable(PageRequest.of(0, 1000));

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public <T> List<T> list(Pair<String, Object> fieldValuePair, Class<T> clazz) {
        SearchQuery searchQuery = new NativeSearchQuery(
                QueryBuilders.termQuery(fieldValuePair.getKey(), fieldValuePair.getValue()));
        searchQuery.setPageable(PageRequest.of(0, 1000));

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public <T> List<T> list(Collection<Pair<String, Object>> fieldValuePairs, Class<T> clazz) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        fieldValuePairs.forEach(o -> boolQueryBuilder.must(QueryBuilders.termQuery(o.getKey(), o.getValue())));
        SearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        searchQuery.setPageable(PageRequest.of(0, 1000));

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public <T> Page<T> page(SearchQuery searchQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchQuery);

        return elasticsearchOperations.queryForPage(searchQuery, clazz);
    }

    @Override
    public <T> Page<T> page(Pair<String, Object> fieldValuePair, Class<T> clazz) {
        SearchQuery searchQuery = new NativeSearchQuery(
                QueryBuilders.termQuery(fieldValuePair.getKey(), fieldValuePair.getValue()));

        return elasticsearchOperations.queryForPage(searchQuery, clazz);
    }

    @Override
    public <T> Page<T> page(Collection<Pair<String, Object>> fieldValuePairs, Class<T> clazz) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        fieldValuePairs.forEach(o -> boolQueryBuilder.must(QueryBuilders.termQuery(o.getKey(), o.getValue())));
        SearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);

        return elasticsearchOperations.queryForPage(searchQuery, clazz);
    }

    @Override
    public <T> T getById(Long id, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(id);

        GetQuery getQuery = GetQuery.getById(String.valueOf(id));
        return elasticsearchOperations.queryForObject(getQuery, clazz);
    }

    @Override
    public <T> List<T> getByIds(Collection<Long> ids, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(ids);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termsQuery("id", ids))
                .build();

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public <T> void index(T document) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(document);

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(document).build();

        elasticsearchOperations.index(indexQuery);
    }

    @Override
    public <T> void index(Collection<T> documents) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documents);

        List<IndexQuery> indexQueries = new ArrayList<>(documents.size());
        for (T document : documents) {
            indexQueries.add(new IndexQueryBuilder().withObject(document).build());
        }

        elasticsearchOperations.bulkIndex(indexQueries);
    }

    @Override
    public <T> void update(Long id, Class<T> clazz, Pair<String, Object> fieldValuePair) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(id);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(fieldValuePair);

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.doc(fieldValuePair.getKey(), fieldValuePair.getValue());

        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(String.valueOf(id));
        updateQuery.setClazz(clazz);
        updateQuery.setUpdateRequest(updateRequest);

        elasticsearchOperations.update(updateQuery);
    }

    @Override
    public <T> void update(Long id, Class<T> clazz, Collection<Pair<String, Object>> fieldValuePairs) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(id);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(fieldValuePairs);

        UpdateRequest updateRequest = new UpdateRequest();
        fieldValuePairs.forEach(o -> updateRequest.doc(o.getKey(), o.getValue()));

        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(String.valueOf(id));
        updateQuery.setClazz(clazz);
        updateQuery.setUpdateRequest(updateRequest);

        elasticsearchOperations.update(updateQuery);
    }

    @Override
    public <T> void update(Collection<Long> ids, Class<T> clazz, Pair<String, Object> fieldValuePair) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(ids);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(fieldValuePair);

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.doc(fieldValuePair.getKey(), fieldValuePair.getValue());

        List<UpdateQuery> updateQueries = ids.stream().map(String::valueOf).map(o -> {
            UpdateQuery updateQuery = new UpdateQuery();
            updateQuery.setId(o);
            updateQuery.setClazz(clazz);
            updateQuery.setUpdateRequest(updateRequest);
            return updateQuery;
        }).collect(Collectors.toList());

        elasticsearchOperations.bulkUpdate(updateQueries);
    }

    @Override
    public <T> void update(Collection<Long> ids, Class<T> clazz, Collection<Pair<String, Object>> fieldValuePairs) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(ids);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(fieldValuePairs);

        UpdateRequest updateRequest = new UpdateRequest();
        fieldValuePairs.forEach(o -> updateRequest.doc(o.getKey(), o.getValue()));

        List<UpdateQuery> updateQueries = ids.stream().map(String::valueOf).map(o -> {
            UpdateQuery updateQuery = new UpdateQuery();
            updateQuery.setId(o);
            updateQuery.setClazz(clazz);
            updateQuery.setUpdateRequest(updateRequest);
            return updateQuery;
        }).collect(Collectors.toList());

        elasticsearchOperations.bulkUpdate(updateQueries);
    }

    @Override
    public <T> void delete(DeleteQuery deleteQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(deleteQuery);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        elasticsearchOperations.delete(deleteQuery, clazz);
    }

    @Override
    public <T> void delete(Long id, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(id);

        elasticsearchOperations.delete(clazz, String.valueOf(id));
    }

    @Override
    public <T> void delete(Collection<Long> ids, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(ids);

        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.termQuery("id", ids));

        elasticsearchOperations.delete(deleteQuery, clazz);
    }

    @Override
    public <T> void deleteIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        elasticsearchOperations.deleteIndex(clazz);
    }
}
