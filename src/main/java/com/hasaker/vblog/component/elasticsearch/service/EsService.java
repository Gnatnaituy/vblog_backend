package com.hasaker.vblog.component.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.util.List;

public interface EsService<T> {

    List<T> search(SearchQuery searchQuery, Class<T> clazz);

    Page<T> page(SearchQuery searchQuery, Class<T> clazz);

    boolean createIndex(Class<T> clazz);

    boolean deleteIndex(Class<T> clazz);

    void indexDocument(T document);

    void bulkIndexDocuments(List<T> documents);

    String deleteDocument(Class<T> clazz, String documentId);

    void deleteDocument(DeleteQuery deleteQuery, Class<T> clazz);

    List<String> bulkDeleteDocument(Class<T> clazz, List<String> documentIds);

    void updateDocument(UpdateQuery updateQuery);

    void bulkUpdateDocuments(List<UpdateQuery> updateQueries);
}
