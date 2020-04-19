package com.hasaker.component.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @package com.hasaker.component.qiniu.config
 * @author 余天堂
 * @create 2020/4/14 23:22
 * @description AliyunConfig
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "oss", value = "type", havingValue = "aliyun", matchIfMissing = true)
public class AliyunConfig {

    @Value("${aliyun.end-point}")
    private String END_POINT;

    @Value("${aliyun.access-key-id}")
    private String ACCESS_KEY_ID;

    @Value("${aliyun.access-key-secret}")
    private String ACCESS_KEY_SECRET;

    @Bean
    @ConditionalOnBean(AliyunConfig.class)
    public OSS ossClient() {
        log.info("ALIYUN OSS CLIENT INITIALIZED");
        return new OSSClientBuilder().build(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }
}
