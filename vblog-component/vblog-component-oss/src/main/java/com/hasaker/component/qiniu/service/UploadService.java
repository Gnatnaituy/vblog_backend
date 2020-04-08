package com.hasaker.component.qiniu.service;

import com.hasaker.common.vo.Ajax;
import org.springframework.web.multipart.MultipartFile;

/**
 * @package com.hasaker.component.qiniu.service
 * @author 余天堂
 * @create 2020/4/8 18:50
 * @description UploadService
 */
public interface UploadService {

    Ajax<String> upload(MultipartFile multipartFile);

    Ajax<String> generateAccessUrl(String key);

    Ajax delete(String key);

    String getkey(String publicUrl);
}
