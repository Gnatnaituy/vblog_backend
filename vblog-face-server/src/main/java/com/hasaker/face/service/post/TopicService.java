package com.hasaker.face.service.post;

import com.hasaker.face.vo.response.ResponseTopicDetailVo;
import com.hasaker.face.vo.response.ResponseTopicInfoVo;

import java.util.List;

/**
 * @package com.hasaker.face.service.post
 * @author 余天堂
 * @create 2020/5/3 02:49
 * @description TopicService
 */
public interface TopicService {

    ResponseTopicDetailVo detail(Long topicId);

    List<ResponseTopicInfoVo> getUserTopics(Long userId);
}
