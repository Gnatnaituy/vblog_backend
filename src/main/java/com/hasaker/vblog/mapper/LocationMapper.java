package com.hasaker.vblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasaker.vblog.entity.Location;
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
