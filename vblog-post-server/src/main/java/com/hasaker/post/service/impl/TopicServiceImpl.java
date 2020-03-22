package com.hasaker.post.service.impl;

import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.post.entity.Topic;
import com.hasaker.post.exception.enums.PostExceptionEnum;
import com.hasaker.post.mapper.TopicMapper;
import com.hasaker.post.service.TopicService;
import com.hasaker.post.vo.request.RequestTopicVo;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description TopicServiceImpl
 */
@Service
public class TopicServiceImpl extends BaseServiceImpl<TopicMapper, Topic> implements TopicService {

    /**
     * Update topic's description
     * @param topicVo
     */
    @Override
    public void update(RequestTopicVo topicVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(topicVo);

        Topic topic = this.getById(topicVo.getTopicId());
        PostExceptionEnum.TOPIC_NOT_EXISTS.assertNotEmpty(topic);

        topic.setDescription(topicVo.getDescription());

        this.updateById(topic);
    }
}
