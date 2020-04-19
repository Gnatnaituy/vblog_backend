package com.hasaker.component.oss.service.impl;

import com.aliyun.oss.OSS;
import com.hasaker.common.config.SnowFlakeIdGenerator;
import com.hasaker.component.oss.config.AliyunConfig;
import com.hasaker.component.oss.service.UploadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

/**
 * @package com.hasaker.component.qiniu.service.impl
 * @author 余天堂
 * @create 2020/4/14 23:59
 * @description AliyunUploadServiceImpl
 */
@Slf4j
@Service
@ConditionalOnBean(value = AliyunConfig.class)
public class AliyunUploadServiceImpl implements UploadService {

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;
    @Autowired
    private OSS ossClient;

    @Value("${aliyun.bucket}")
    private String BUCKET;

    @Override
    @SneakyThrows
    public String upload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        String key = snowFlakeIdGenerator.nextUUID(multipartFile) + suffix;

        InputStream inputStream = multipartFile.getInputStream();
        ossClient.putObject(BUCKET, key, inputStream);

        return generateAccessUrl(key);
    }

    @Override
    public String generateAccessUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);

        return ossClient.generatePresignedUrl(BUCKET, key, expiration).toString();
    }

    @Override
    public String getKey(String publicUrl) {
        String decodedPublicUrl = URLDecoder.decode(publicUrl, StandardCharsets.UTF_8);

        return decodedPublicUrl.substring(decodedPublicUrl.lastIndexOf("com/") + 4).split("\\?")[0];
    }

    @Override
    public boolean delete(String key) {
        ossClient.deleteObject(BUCKET, key);
        return true;
    }
}
