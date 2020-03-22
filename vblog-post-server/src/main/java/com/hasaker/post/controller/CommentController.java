package com.hasaker.post.controller;

import com.hasaker.common.vo.Ajax;
import com.hasaker.post.service.CommentService;
import com.hasaker.post.vo.request.RequestCommentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.post.controller
 * @author 余天堂
 * @create 2020/3/22 19:51
 * @description CommentController
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Create a comment")
    @PostMapping(value = "/save")
    Ajax save(@RequestBody RequestCommentVo commentVo) {
        commentService.comment(commentVo);
        return Ajax.success();
    }

    @ApiOperation(value = "Delete a comment")
    @DeleteMapping(value = "/{commentId}")
    Ajax delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return Ajax.success();
    }
}