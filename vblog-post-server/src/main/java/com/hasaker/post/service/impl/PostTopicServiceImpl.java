package com.hasaker.post.service.impl;

import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.post.entity.PostTopic;
import com.hasaker.post.mapper.PostTopicMapper;
import com.hasaker.post.service.PostTopicService;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description PostTopicServiceImpl
 */
@Service
public class PostTopicServiceImpl extends BaseServiceImpl<PostTopicMapper, PostTopic> implements PostTopicService {
}
