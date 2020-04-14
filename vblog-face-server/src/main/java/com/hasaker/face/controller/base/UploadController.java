package com.hasaker.face.controller.base;

import com.hasaker.common.vo.Ajax;
import com.hasaker.component.qiniu.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @package com.hasaker.face.controller.base
 * @author 余天堂
 * @create 2020/4/8 19:18
 * @description UploadController
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    Ajax<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        return Ajax.getInstance().successT(uploadService.upload(multipartFile));
    }
}
