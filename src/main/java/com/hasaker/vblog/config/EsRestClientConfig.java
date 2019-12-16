package com.hasaker.vblog.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author 余天堂
 * @since 2019/11/17 19:28
 * @description
 */
public class EsRestClientConfig extends AbstractElasticsearchConfiguration {

    public RestHighLevelClient elasticsearchClient() {

        return RestClients.create(ClientConfiguration.localhost()).rest();
    }
}
