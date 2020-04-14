package com.hasaker.component.qiniu.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @package com.hasaker.component.qiniu.config
 * @author 余天堂
 * @create 2020/4/8 17:21
 * @description QiniuConfig
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "oss", value = "type", havingValue = "qiniu")
public class QiniuyunConfig {

    @Value("${qiniu.access-key}")
    private String ACCESS_KEY;

    @Value("${qiniu.secret-key}")
    private String SECRET_KEY;

    @Bean
    @ConditionalOnBean(QiniuyunConfig.class)
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Region.huadong());
    }

    /**
     * Construct authorization instance
     */
    @Bean
    @ConditionalOnBean(QiniuyunConfig.class)
    public Auth auth() {
        return Auth.create(ACCESS_KEY, SECRET_KEY);
    }

    /**
     * Construct upload manager instance
     */
    @Bean
    @ConditionalOnBean(QiniuyunConfig.class)
    public UploadManager uploadManager() {
        log.info("QINIUYUN OSS CLIENT INITIALIZED");
        return new UploadManager(qiniuConfig());
    }

    /**
     * Construct bucket manager instance
     */
    @Bean
    @ConditionalOnBean(QiniuyunConfig.class)
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }
}
