package com.hasaker.face.controller.post;

import com.hasaker.common.vo.Ajax;
import com.hasaker.face.controller.base.BaseController;
import com.hasaker.face.service.post.VoteService;
import com.hasaker.face.vo.response.message.ResponseMessageVoteVo;
import com.hasaker.post.feign.PostClient;
import com.hasaker.post.vo.request.RequestVoteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @package com.hasaker.face.controller.post
 * @author 余天堂
 * @create 2020/3/26 22:58
 * @description VoteController
 */
@RestController
@RequestMapping("/post/vote")
@Slf4j
public class VoteController extends BaseController {

    @Autowired
    private PostClient postClient;
    @Autowired
    private VoteService voteService;

    @PostMapping("/vote")
    Ajax vote(@RequestBody RequestVoteVo voteVo) {
        voteVo.setVoter(getUserId());

        return postClient.vote(voteVo);
    }

    @GetMapping("/messages")
    Ajax<List<ResponseMessageVoteVo>> listUnreadVoteMessage() {
        return Ajax.getInstance().successT(voteService.listMessage(getUserId()));
    }
}
