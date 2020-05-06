package com.hasaker.face.controller.base;

import com.hasaker.common.consts.Consts;
import com.hasaker.common.vo.Ajax;
import com.hasaker.component.oss.service.UploadService;
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
@RequestMapping("/open/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/image/post-image")
    Ajax<String> uploadPostImage(@RequestParam("file") MultipartFile multipartFile) {

        return Ajax.getInstance().successT(uploadService.upload(multipartFile, Consts.POST_IMAGE));
    }

    @PostMapping("/image/user-avatar")
    Ajax<String> uploadUserAvatar(@RequestParam("file") MultipartFile multipartFile) {

        return Ajax.getInstance().successT(uploadService.upload(multipartFile, Consts.USER_AVATAR));
    }

    @PostMapping("/image/user-background")
    Ajax<String> uploadUserBackground(@RequestParam("file") MultipartFile multipartFile) {

        return Ajax.getInstance().successT(uploadService.upload(multipartFile, Consts.USER_BACKGROUND));
    }

    @PostMapping("/image/topic-background")
    Ajax<String> uploadTopicBackground(@RequestParam("file") MultipartFile multipartFile) {

        return Ajax.getInstance().successT(uploadService.upload(multipartFile, Consts.TOPIC_BACKGROUND));
    }
}
