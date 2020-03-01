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
public class EsServiceImpl<T> implements EsService<T> {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<T> search(SearchQuery searchQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(searchQuery);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public Page<T> page(SearchQuery searchQuery, Class<T> clazz) {

        return elasticsearchOperations.queryForPage(searchQuery, clazz);
    }

    @Override
    public boolean createIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        return elasticsearchOperations.putMapping(clazz) && elasticsearchOperations.createIndex(clazz);
    }

    @Override
    public boolean deleteIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        return elasticsearchOperations.deleteIndex(clazz);
    }

    @Override
    public void indexDocument(T document) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(document);

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(document).build();

        elasticsearchOperations.index(indexQuery);
    }

    @Override
    public void bulkIndexDocuments(List<T> documents) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documents);

        List<IndexQuery> indexQueries = new ArrayList<>(documents.size());
        for (T document : documents) {
            indexQueries.add(new IndexQueryBuilder().withObject(document).build());
        }

        elasticsearchOperations.bulkIndex(indexQueries);
    }

    @Override
    public String deleteDocument(Class<T> clazz, String documentId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documentId);

        return elasticsearchOperations.delete(clazz, documentId);
    }

    @Override
    public void deleteDocument(DeleteQuery deleteQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(deleteQuery);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);

        elasticsearchOperations.delete(deleteQuery, clazz);
    }

    @Override
    public List<String> bulkDeleteDocument(Class<T> clazz, List<String> documentIds) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(clazz);
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(documentIds);

        List<String> deletedDocumentIds = new ArrayList<>(documentIds.size());
        for (String documentId : documentIds) {
            deletedDocumentIds.add(elasticsearchOperations.delete(clazz, documentId));
        }

        return deletedDocumentIds;
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
