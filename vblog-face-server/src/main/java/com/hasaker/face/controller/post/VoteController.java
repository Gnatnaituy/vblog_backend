package com.hasaker.face.controller.post;

import com.hasaker.common.vo.Ajax;
import com.hasaker.face.controller.base.BaseController;
import com.hasaker.post.feign.PostClient;
import com.hasaker.post.vo.request.RequestVoteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @package com.hasaker.face.controller.post
 * @author 余天堂
 * @create 2020/3/26 22:58
 * @description VoteController
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class VoteController extends BaseController {

    @Autowired
    private PostClient postClient;

    @PostMapping("/vote/vote")
    Ajax vote(@RequestBody RequestVoteVo voteVo) {
        voteVo.setVoter(getUserId());

        log.info("VOTE AT {}", new Date().getTime());

        return postClient.vote(voteVo);
    }
}
