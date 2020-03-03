package com.hasaker.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @package com.hasaker.vblog.handler
 * @author 余天堂
 * @create 2020/1/2 14:28
 * @description AutoMetaObjectHandler
 */
@Component
public class AutoMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private HttpServletRequest request;

    private final String CREATE_User = "createUser";
    private final String CREATE_TIME = "createTime";
    private final String UPDATE_USER = "updateUser";
    private final String UPDATE_TIME = "updateTime";

    /**
     * 插入记录自动填充字段
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(CREATE_TIME, System.currentTimeMillis(), metaObject);
        this.setFieldValByName(UPDATE_TIME, System.currentTimeMillis(), metaObject);
    }

    /**
     * 更新记录自动填充字段
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(UPDATE_TIME, System.currentTimeMillis(), metaObject);
    }
}
