package com.hasaker.component.oss.service.impl;

import com.hasaker.common.config.SnowFlakeIdGenerator;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.oss.config.QiniuyunConfig;
import com.hasaker.component.oss.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @package com.hasaker.component.qiniu.service.impl
 * @author 余天堂
 * @create 2020/4/8 18:54
 * @description QiniuUploadServiceImpl
 */
@Slf4j
@Service
@ConditionalOnBean(value = QiniuyunConfig.class)
public class QiniuUploadServiceImpl implements UploadService {

    @Autowired
    private BucketManager bucketManager;
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private Auth auth;
    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Value("${qiniu.bucket}")
    private String BUCKET;
    @Value("${qiniu.domain}")
    private String DOMAIN;
    @Value("${qiniu.retry-times}")
    private Integer RETRY_TIMES;

    /**
     * Upload by File type
     * @param multipartFile
     * @return
     * @throws QiniuException
     */
    @Override
    public String upload(MultipartFile multipartFile, String dir) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        String key = dir + snowFlakeIdGenerator.nextUUID(multipartFile) + suffix;

        try {
            Response response = this.uploadManager.put(multipartFile.getBytes(), key, auth.uploadToken(BUCKET));
            int retry = 0;
            while (response.needRetry() && retry < RETRY_TIMES) {
                response = this.uploadManager.put(multipartFile.getBytes(), key, auth.uploadToken(BUCKET));
                retry++;
            }

            if (response.statusCode == 200) {
                return generateAccessUrl(key);
            } else {
                throw CommonExceptionEnums.INTERNAL_SERVER_ERROR.newException();
            }
        } catch (IOException e) {
            throw CommonExceptionEnums.INTERNAL_SERVER_ERROR.newException();
        }
    }

    /**
     * Generate public accessible temporary url
     * @param key
     * @return
     */
    @Override
    @SneakyThrows
    public String generateAccessUrl(String key) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(key);

        String encodedFileName = URLEncoder.encode(key, StandardCharsets.UTF_8);
        String publicUrl = String.format("%s/%s", DOMAIN, encodedFileName);

        return auth.privateDownloadUrl(publicUrl, 3600);
    }

    /**
     * Get key from public accessible url
     * @param publicUrl
     * @return
     */
    @Override
    public String getKey(String publicUrl) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(publicUrl);

        String decodedPublicUrl = URLDecoder.decode(publicUrl, StandardCharsets.UTF_8);

        return decodedPublicUrl.substring(decodedPublicUrl.lastIndexOf("com/") + 1).split("\\?")[0];
    }

    /**
     * Delete file from qiniu
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public boolean delete(String key) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(key);

        try {
            Response response = bucketManager.delete(this.BUCKET, key);
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(BUCKET, key);
            }
            return response.statusCode == 200;
        } catch (QiniuException e) {
            log.error(e.getLocalizedMessage(), e);
            throw CommonExceptionEnums.INTERNAL_SERVER_ERROR.newException();
        }
    }
}
