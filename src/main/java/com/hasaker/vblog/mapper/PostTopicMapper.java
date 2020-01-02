package com.hasaker.vblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.vblog.entity.PostTopic;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2020/1/2 16:49
 * @description PostTopicMapper
 */
@Mapper
public interface PostTopicMapper extends BaseMapper<PostTopic> {
}
