package com.hasaker.vblog.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author 余天堂
 * @since 2019/10/31 17:18
 */
public interface BaseMapper<T> {

    int insert(T entity);

    int deleteById(Serializable id);

    int delete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    int updateById(@Param(Constants.ENTITY) T entity);

    int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

    T selectById(Serializable id);

    Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
