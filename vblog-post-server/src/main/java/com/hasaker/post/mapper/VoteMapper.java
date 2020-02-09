package com.hasaker.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.user.entity.Vote;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2019/12/24 18:08
 * @description VoteMapper
 */
@Mapper
public interface VoteMapper extends BaseMapper<Vote> {
}
