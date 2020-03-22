package com.hasaker.post.controller;

import com.hasaker.common.vo.Ajax;
import com.hasaker.post.service.TopicService;
import com.hasaker.post.vo.request.RequestTopicVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.post.controller
 * @author 余天堂
 * @create 2020/3/22 21:00
 * @description TopicController
 */
@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @ApiOperation(value = "Update topic's description")
    @PostMapping(value = "/update")
    Ajax update(@RequestBody RequestTopicVo topicVo) {
        topicService.update(topicVo);
        return Ajax.success();
    }
}
