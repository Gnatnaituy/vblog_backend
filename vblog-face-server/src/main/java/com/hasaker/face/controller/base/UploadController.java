package com.hasaker.face.controller.base;

import com.hasaker.common.consts.RequestConsts;
import com.hasaker.common.vo.Ajax;
import com.hasaker.component.oss.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/image")
    Ajax<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        String dir = request.getHeader(RequestConsts.DIRECTORY);

        return Ajax.getInstance().successT(uploadService.upload(multipartFile, dir));
    }
}
