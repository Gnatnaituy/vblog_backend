package com.hasaker.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.common.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2019/12/24 18:05
 * @description CommentMapper
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
