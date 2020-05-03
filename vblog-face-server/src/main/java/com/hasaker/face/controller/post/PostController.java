package com.hasaker.face.controller.post;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.vo.Ajax;
import com.hasaker.component.oss.service.UploadService;
import com.hasaker.face.service.post.PostService;
import com.hasaker.face.vo.request.RequestMessageReadVo;
import com.hasaker.post.feign.PostClient;
import com.hasaker.post.vo.request.RequestPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.face.controller.post
 * @author 余天堂
 * @create 2020/2/27 09:42
 * @description PostController
 */
@RestController
@RequestMapping("/post/post")
public class PostController {

    @Autowired
    private PostClient postClient;
    @Autowired
    private PostService postService;
    @Autowired
    private UploadService uploadService;

    @PostMapping("/save")
    Ajax save(@RequestBody RequestPostVo postVo) {
        if (ObjectUtils.isNotNull(postVo.getImages())) {
            postVo.getImages().forEach(o -> o.setUrl(uploadService.getKey(o.getUrl())));
        }

        return postClient.savePost(postVo);
    }

    @DeleteMapping("/{postId}")
    Ajax delete(@PathVariable("postId") Long postId) {
        return postClient.deletePost(postId);
    }

    @PostMapping("/read-message")
    Ajax readMessage(@RequestBody RequestMessageReadVo readVo) {
        postService.readMessage(readVo);

        return Ajax.success();
    }
}
