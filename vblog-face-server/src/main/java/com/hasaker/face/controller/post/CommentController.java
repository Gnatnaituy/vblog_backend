package com.hasaker.face.controller.post;

import com.hasaker.common.vo.Ajax;
import com.hasaker.post.feign.PostClient;
import com.hasaker.post.vo.request.RequestCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.face.controller.post
 * @author 余天堂
 * @create 2020/3/26 22:57
 * @description CommentController
 */
@RestController
@RequestMapping("/post")
public class CommentController {

    @Autowired
    private PostClient postClient;

    @PostMapping("/comment/save")
    Ajax save(@RequestBody RequestCommentVo commentVo) {
        return postClient.saveComment(commentVo);
    }

    @DeleteMapping("/comment/{commentId}")
    Ajax delete(@RequestParam("commentId") Long commentId) {
        return postClient.deleteComment(commentId);
    }
}
