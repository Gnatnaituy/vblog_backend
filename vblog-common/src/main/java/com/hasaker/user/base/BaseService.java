package com.hasaker.user.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 余天堂
 * @since 2019/10/31 17:11
 */
public interface BaseService <T extends Serializable> {

    /**
     * 批量大小
     */
    int batchSize = 1024;

    /**
     * 新增对象后返回对象信息
     */
    T saveId(T entity);

    /**
     * 保存实体
     */
    boolean save(T entity);

    /**
     * 更新
     */
    boolean updateById(T entity);

    /**
     * 根据条件更新
     */
    boolean update(T entity, Wrapper<T> queryWrapper);

    /**
     * 删除
     */
    boolean removeById(Serializable id);

    /**
     * 根据条件删除
     */
    boolean remove(Wrapper<T> queryWrapper);

    /**
     * 批量保存，指定长度
     */
    boolean saveBatch(Collection<T> entities, int batchSize);

    /**
     * 批量保存,使用默认长度
     */
    default boolean saveBatch(Collection<T> entities) {
        return saveBatch(entities, batchSize);
    }

    /**
     * 查询总数
     */
    int count(Wrapper<T> queryWrapper);

    /**
     * 查询总数
     */
    default int count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * 通过主键查询对象
     */
    T getById(Serializable id);

    /**
     * 根据条件查询唯一结果
     */
    T getOne(Wrapper<T> queryWrapper);

    /**
     * 根据条件查询列表
     */
    List<T> list(Wrapper<T> queryWrapper);

    /**
     * 查询集合
     */
    default List<T> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 分页查询
     */
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 分页查询
     */
    default IPage<T> page(IPage<T> page) {
        return page(page, Wrappers.emptyWrapper());
    }
}
