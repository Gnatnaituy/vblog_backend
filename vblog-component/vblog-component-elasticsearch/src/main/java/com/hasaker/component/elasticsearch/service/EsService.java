package com.hasaker.component.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.util.List;

public interface EsService {

    <T> List<T> search(SearchQuery searchQuery, Class<T> clazz);

    <T> Page<T> page(SearchQuery searchQuery, Class<T> clazz);

    <T> void createIndex(Class<T> clazz);

    <T> void deleteIndex(Class<T> clazz);

    <T> void indexDocument(T document);

    <T> void bulkIndexDocuments(List<T> documents);

    <T> void deleteDocument(String documentId, Class<T> clazz);

    <T> void deleteDocument(DeleteQuery deleteQuery, Class<T> clazz);

    <T> void bulkDeleteDocument(List<String> documentIds, Class<T> clazz);

    void updateDocument(UpdateQuery updateQuery);

    void bulkUpdateDocuments(List<UpdateQuery> updateQueries);
}
