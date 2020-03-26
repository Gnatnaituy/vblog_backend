package com.hasaker.component.elasticsearch.service;

import cn.hutool.core.lang.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Collection;
import java.util.List;

public interface EsService {

    <T> List<T> list(SearchQuery searchQuery, Class<T> clazz);

    <T> List<T> list(String field, Object value, Class<T> clazz);

    <T> List<T> list(Collection<Pair<String, Object>> fieldValuePairs, Class<T> clazz);

    <T> Page<T> page(SearchQuery searchQuery, Class<T> clazz);

    <T> Page<T> page(String field, Object value, Class<T> clazz);

    <T> Page<T> page(Collection<Pair<String, Object>> fieldValuePairs, Class<T> clazz);

    <T> T getById(String id, Class<T> clazz);

    <T> List<T> getByIds(Collection<String> ids, Class<T> clazz);

    <T> void index(T document);

    <T> void index(Collection<T> documents);

    <T> void update(String id, Class<T> clazz, Pair<String, Object> fieldValuePair);

    <T> void update(String id, Class<T> clazz, Collection<Pair<String, Object>> fieldValuePairs);

    <T> void update(Collection<String> ids, Class<T> clazz, Pair<String, Object> fieldValuePair);

    <T> void update(Collection<String> ids, Class<T> clazz, Collection<Pair<String, Object>> fieldValuePairs);

    <T> void delete(DeleteQuery deleteQuery, Class<T> clazz);

    <T> void delete(String id, Class<T> clazz);

    <T> void delete(Collection<String> ids, Class<T> clazz);
}
