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

    /**
     * Upload a file to Aliyun OSS
     * @param multipartFile
     * @return A public accessible temporary url
     */
    @Override
    @SneakyThrows
    public String upload(MultipartFile multipartFile, String dir) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        String key = dir + snowFlakeIdGenerator.nextUUID(multipartFile) + suffix;

        InputStream inputStream = multipartFile.getInputStream();
        ossClient.putObject(BUCKET, key, inputStream);

        return generateAccessUrl(key);
    }

    /**
     * Generate a public accessible temporary url for a file key
     * @param key
     * @return
     */
    @Override
    public String generateAccessUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);

        return ossClient.generatePresignedUrl(BUCKET, key, expiration).toString();
    }

    /**
     * Get file key from a public url
     * @param publicUrl
     * @return
     */
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
