package com.hasaker.face.controller.post;

import com.hasaker.common.vo.Ajax;
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
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostClient postClient;

    @PostMapping("/post/save")
    Ajax save(@RequestBody RequestPostVo postVo) {
        return postClient.savePost(postVo);
    }

    @DeleteMapping("/post/{postId}")
    Ajax delete(@RequestParam("postId") Long postId) {
        return postClient.deletePost(postId);
    }
}
