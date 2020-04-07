package com.hasaker.face.controller.post;

import com.hasaker.common.vo.Ajax;
import com.hasaker.face.service.post.CommentService;
import com.hasaker.face.vo.response.ResponsePostCommentVo;
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
    private CommentService commentService;
    @Autowired
    private PostClient postClient;

    @PostMapping("/comment/save")
    Ajax<ResponsePostCommentVo> save(@RequestBody RequestCommentVo commentVo) {
        Long commentId = postClient.saveComment(commentVo).getData();
        return Ajax.getInstance().successT(commentService.getById(commentId));
    }

    @DeleteMapping("/comment/{commentId}")
    Ajax delete(@PathVariable Long commentId) {
        return postClient.deleteComment(commentId);
    }
}
