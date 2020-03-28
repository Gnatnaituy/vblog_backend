package com.hasaker.face.controller.post;

import com.hasaker.common.vo.Ajax;
import com.hasaker.post.feign.PostClient;
import com.hasaker.post.vo.request.RequestTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.face.controller.post
 * @author 余天堂
 * @create 2020/3/26 22:58
 * @description TopicController
 */
@RestController
@RequestMapping("/post")
public class TopicController {

    @Autowired
    private PostClient postClient;

    @PostMapping("/topic/vote")
    Ajax save(@RequestBody RequestTopicVo topicVo) {
        return postClient.saveTopic(topicVo);
    }
}
