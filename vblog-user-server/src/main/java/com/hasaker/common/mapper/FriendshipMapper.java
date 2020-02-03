package com.hasaker.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.common.entity.Friendship;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2019/12/24 18:06
 * @description FriendshipMapper
 */
@Mapper
public interface FriendshipMapper extends BaseMapper<Friendship> {
}
