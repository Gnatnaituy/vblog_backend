package com.hasaker.component.elasticsearch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(searchQuery), "searchQuery");
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(clazz), "clazz");

        return elasticsearchOperations.queryForList(searchQuery, clazz);
    }

    @Override
    public Page<T> page(SearchQuery searchQuery, Class<T> clazz) {

        return elasticsearchOperations.queryForPage(searchQuery, clazz);
    }

    @Override
    public boolean createIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(clazz), "clazz");

        return elasticsearchOperations.putMapping(clazz) && elasticsearchOperations.createIndex(clazz);
    }

    @Override
    public boolean deleteIndex(Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(clazz), "clazz");

        return elasticsearchOperations.deleteIndex(clazz);
    }

    @Override
    public void indexDocument(T document) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(document), "document");

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(document).build();

        elasticsearchOperations.index(indexQuery);
    }

    @Override
    public void bulkIndexDocuments(List<T> documents) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(documents), "documents");

        List<IndexQuery> indexQueries = new ArrayList<>(documents.size());
        for (T document : documents) {
            indexQueries.add(new IndexQueryBuilder().withObject(document).build());
        }

        elasticsearchOperations.bulkIndex(indexQueries);
    }

    @Override
    public String deleteDocument(Class<T> clazz, String documentId) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(clazz), "clazz");
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(StringUtils.isEmpty(documentId), "documentId");

        return elasticsearchOperations.delete(clazz, documentId);
    }

    @Override
    public void deleteDocument(DeleteQuery deleteQuery, Class<T> clazz) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(deleteQuery), "deleteQuery");
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(clazz), "clazz");

        elasticsearchOperations.delete(deleteQuery, clazz);
    }

    @Override
    public List<String> bulkDeleteDocument(Class<T> clazz, List<String> documentIds) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(clazz), "clazz");
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(documentIds), "documentIds");

        List<String> deletedDocumentIds = new ArrayList<>(documentIds.size());
        for (String documentId : documentIds) {
            deletedDocumentIds.add(elasticsearchOperations.delete(clazz, documentId));
        }

        return deletedDocumentIds;
    }

    @Override
    public void updateDocument(UpdateQuery updateQuery) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(updateQuery), "updateQuery");

        elasticsearchOperations.update(updateQuery);
    }

    @Override
    public void bulkUpdateDocuments(List<UpdateQuery> updateQueries) {
        CommonExceptionEnums.NOT_NULL_ARG.isTrue(ObjectUtils.isNull(updateQueries), "updateQueries");

        elasticsearchOperations.bulkUpdate(updateQueries);
    }
}
