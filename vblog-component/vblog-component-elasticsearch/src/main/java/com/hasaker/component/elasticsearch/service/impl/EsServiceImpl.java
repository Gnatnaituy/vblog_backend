package com.hasaker.component.elasticsearch.service.impl;

import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public <T> List<T> search(SearchQuery searchQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchQuery);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public <T> Page<T> page(SearchQuery searchQuery, Class<T> clazz) {

       return elasticsearchOperations.queryForPage(searchQuery, clazz);
    }

    @Override
    public <T> void createIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        elasticsearchOperations.putMapping(clazz);
        elasticsearchOperations.createIndex(clazz);
    }

    @Override
    public <T> void deleteIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        elasticsearchOperations.deleteIndex(clazz);
    }

    @Override
    public <T> void indexDocument(T document) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(document);

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(document).build();

        elasticsearchOperations.index(indexQuery);
    }

    @Override
    public <T> void bulkIndexDocuments(List<T> documents) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documents);

        List<IndexQuery> indexQueries = new ArrayList<>(documents.size());
        for (T document : documents) {
            indexQueries.add(new IndexQueryBuilder().withObject(document).build());
        }

        elasticsearchOperations.bulkIndex(indexQueries);
    }

    @Override
    public <T> void deleteDocument(String documentId, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documentId);

        elasticsearchOperations.delete(clazz, documentId);
    }

    @Override
    public <T> void deleteDocument(DeleteQuery deleteQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(deleteQuery);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        elasticsearchOperations.delete(deleteQuery, clazz);
    }

    @Override
    public <T> void bulkDeleteDocument(List<String> documentIds, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documentIds);

        for (String documentId : documentIds) {
            elasticsearchOperations.delete(clazz, documentId);
        }
    }

    @Override
    public void updateDocument(UpdateQuery updateQuery) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(updateQuery);

        elasticsearchOperations.update(updateQuery);
    }

    @Override
    public void bulkUpdateDocuments(List<UpdateQuery> updateQueries) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(updateQueries);

        elasticsearchOperations.bulkUpdate(updateQueries);
    }
}
