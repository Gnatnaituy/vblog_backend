package com.hasaker.component.elasticsearch.service;

import cn.hutool.core.lang.Pair;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Collection;
import java.util.List;

public interface EsService {

    <T> List<T> list(SearchQuery searchQuery, Class<T> clazz);

    <T> List<T> list(QueryBuilder queryBuilder, Class<T> clazz);

    <T> List<T> list(Pair<String, Object> fieldValuePair, Class<T> clazz);

    <T> List<T> list(Collection<Pair<String, Object>> fieldValuePairs, Class<T> clazz);

    <T> Page<T> page(SearchQuery searchQuery, Class<T> clazz);

    <T> Page<T> page(Pair<String, Object> fieldValuePair, Class<T> clazz);

    <T> Page<T> page(Collection<Pair<String, Object>> fieldValuePairs, Class<T> clazz);

    <T> T getById(Long id, Class<T> clazz);

    <T> List<T> getByIds(Collection<Long> ids, Class<T> clazz);

    <T> void index(T document);

    <T> void index(Collection<T> documents);

    <T> void update(Long id, Class<T> clazz, Pair<String, Object> fieldValuePair);

    <T> void update(Long id, Class<T> clazz, Collection<Pair<String, Object>> fieldValuePairs);

    <T> void update(Collection<Long> ids, Class<T> clazz, Pair<String, Object> fieldValuePair);

    <T> void update(Collection<Long> ids, Class<T> clazz, Collection<Pair<String, Object>> fieldValuePairs);

    <T> void delete(DeleteQuery deleteQuery, Class<T> clazz);

    <T> void delete(Long id, Class<T> clazz);

    <T> void delete(Collection<Long> ids, Class<T> clazz);

    <T> void createIndex(Class<T> clazz);

    <T> void deleteIndex(Class<T> clazz);
}
