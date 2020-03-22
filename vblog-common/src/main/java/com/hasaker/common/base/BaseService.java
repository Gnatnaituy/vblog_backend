package com.hasaker.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 余天堂
 * @since 2019/10/31 17:11
 */
public interface BaseService<T> {

    boolean save(T entity);

    boolean save(Collection<T> entities);

    T saveId(T entity);

    boolean update(T entity, Wrapper<T> queryWrapper);

    boolean updateById(T entity);

    boolean remove(Wrapper<T> queryWrapper);

    boolean removeById(Serializable id);

    int count(Wrapper<T> queryWrapper);

    T getById(Serializable id);

    T getOne(Wrapper<T> queryWrapper);

    List<T> list(Wrapper<T> queryWrapper);

    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
}
