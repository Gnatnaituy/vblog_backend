package com.hasaker.component.qiniu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @package com.hasaker.component.qiniu.service
 * @author 余天堂
 * @create 2020/4/8 18:50
 * @description UploadService
 */
public interface UploadService {

    String upload(MultipartFile multipartFile);

    String generateAccessUrl(String key);

    String getKey(String publicUrl);

    boolean delete(String key);
}
