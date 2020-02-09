package com.hasaker.user.base.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.hasaker.user.base.BaseMapper;
import com.hasaker.user.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 余天堂
 * @since 2019/10/31 19:23
 */
@Transactional(readOnly = true)
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends Serializable> implements BaseService<T> {

    @Autowired
    private M baseMapper;

    /**
     * 新增对象后返回对象信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public T saveId(T entity) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);

        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            batchSqlSession.insert(sqlStatement, entity);
            batchSqlSession.flushStatements();

            return entity;
        }
    }

    /**
     * 保存实体
     *
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T entity) {

        return retBool(baseMapper.insert(entity));
    }

    /**
     * 批量保存，指定长度
     *
     * @param entities
     * @param batchSize
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entities, int batchSize) {
        if (CollectionUtils.isEmpty(entities) || batchSize <= 0) {
            log.info("No data in batch list");
            return false;
        }

        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (T entity : entities) {
                batchSqlSession.insert(sqlStatement, entity);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }

        return true;
    }

    /**
     * 更新
     *
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {

        return retBool(baseMapper.updateById(entity));
    }

    /**
     * 根据条件更新
     *
     * @param entity
     * @param queryWrapper
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(T entity, Wrapper<T> queryWrapper) {

        return retBool(baseMapper.update(entity, queryWrapper));
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {

        return retBool(baseMapper.deleteById(id));
    }

    /**
     * 根据条件删除
     *
     * @param queryWrapper
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(Wrapper<T> queryWrapper) {

        return retBool(baseMapper.delete(queryWrapper));
    }

    /**
     * 查询总数
     *
     * @param queryWrapper
     */
    @Override
    public int count(Wrapper<T> queryWrapper) {

        return SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
    }

    /**
     * 通过id获取对象
     * @param id
     * @return
     */
    @Override
    public T getById(Serializable id) {

        return baseMapper.selectById(id);
    }

    /**
     * 根据条件查询唯一结果
     *
     * @param queryWrapper
     */
    @Override
    public T getOne(Wrapper<T> queryWrapper) {

        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据条件查询列表
     *
     * @param queryWrapper
     */
    @Override
    public List<T> list(Wrapper<T> queryWrapper) {

        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param queryWrapper
     */
    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {

        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 获取SqlStatement
     */
    private String sqlStatement(SqlMethod sqlMethod) {

        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 批量操作SqlSession
     */
    private SqlSession sqlSessionBatch() {

        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    private Class<?> currentModelClass() {

        return ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 判断数据库操作是否成功
     */
    private boolean retBool(Integer result) {

        return SqlHelper.retBool(result);
    }
}
