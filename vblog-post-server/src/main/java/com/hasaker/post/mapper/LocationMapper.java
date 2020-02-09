package com.hasaker.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.post.entity.Location;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package com.hasaker.vblog.mapper
 * @author 余天堂
 * @create 2020/1/2 16:48
 * @description LocationMapper
 */
@Mapper
public interface LocationMapper extends BaseMapper<Location> {
}
