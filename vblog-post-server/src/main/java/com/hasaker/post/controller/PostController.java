package com.hasaker.post.controller;

import com.hasaker.common.vo.Ajax;
import com.hasaker.post.service.PostService;
import com.hasaker.post.vo.request.RequestPostVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.post.controller
 * @author 余天堂
 * @create 2020/3/22 19:51
 * @description PostController
 */
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Create a post")
    @PostMapping(value = "/save")
    Ajax<Long> post(@RequestBody RequestPostVo postVo) {
        return Ajax.getInstance().successT(postService.post(postVo));
    }

    @ApiOperation(value = "Delete a post")
    @DeleteMapping(value = "/{postId}")
    Ajax delete(@PathVariable Long postId) {
        postService.delete(postId);
        return Ajax.success();
    }

    @ApiOperation(value = "Index all posts to es")
    @GetMapping(value = "/index-all")
    Ajax indexAllPosts() {
        postService.indexAllPosts();
        return Ajax.success();
    }
}
