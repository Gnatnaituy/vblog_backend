package com.hasaker.post.feign;

import com.hasaker.common.config.FeignExceptionConfig;
import com.hasaker.common.vo.Ajax;
import com.hasaker.post.vo.request.RequestCommentVo;
import com.hasaker.post.vo.request.RequestPostVo;
import com.hasaker.post.vo.request.RequestTopicVo;
import com.hasaker.post.vo.request.RequestVoteVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @package com.hasaker.post.feign
 * @author 余天堂
 * @create 2020/3/23 00:51
 * @description PostClient
 */
@FeignClient(name = "VBLOG-POST", url = "127.0.0.1:9002", configuration = FeignExceptionConfig.class)
@RestController
public interface PostClient {

    @PostMapping(value = "/post/save")
    Ajax savePost(@RequestBody RequestPostVo postVo);

    @DeleteMapping(value = "/post/{postId}")
    Ajax deletePost(@PathVariable("postId") Long postId);

    @PostMapping(value = "/comment/save")
    Ajax saveComment(@RequestBody RequestCommentVo commentVo);

    @DeleteMapping(value = "/comment/{commentId}")
    Ajax deleteComment(@PathVariable("commentId") Long commentId);

    @PostMapping(value = "/vote/vote")
    Ajax vote(@RequestBody RequestVoteVo voteVo);

    @PostMapping(value = "/vote/downvote")
    Ajax downvote(@RequestBody RequestVoteVo voteVo);

    @PostMapping(value = "/topic/update")
    Ajax saveTopic(@RequestBody RequestTopicVo topicVo);
}
