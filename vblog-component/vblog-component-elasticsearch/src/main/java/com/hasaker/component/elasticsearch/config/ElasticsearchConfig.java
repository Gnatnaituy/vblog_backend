package com.hasaker.component.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @package com.hasaker.vblog.config
 * @author 余天堂
 * @create 2019/12/22 01:14
 * @description ElasticsearchAutoConfiguration
 */
@Configuration
public class ElasticsearchConfig {

    /**
     * Get RestHighLevelClient
     * @return {@link org.elasticsearch.client.RestHighLevelClient}
     */
    @Bean
    public RestHighLevelClient getRestHighLevelClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
