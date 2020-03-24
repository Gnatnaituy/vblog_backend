package com.hasaker.post.service;

import com.hasaker.common.base.BaseService;
import com.hasaker.post.entity.Topic;
import com.hasaker.post.vo.request.RequestTopicVo;

/**
 * @package com.hasaker.post.service
 * @author 余天堂
 * @create 2020/3/22 19:40
 * @description TopicService
 */
public interface TopicService extends BaseService<Topic> {

    void update(RequestTopicVo topicVo);
}
