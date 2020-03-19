package com.hasaker.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.vo.RedisAccessToken;
import com.hasaker.component.redis.service.RedisService;
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
    @Autowired
    private RedisService redisService;

    /**
     * 插入记录自动填充字段
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        RedisAccessToken redisAccessToken = this.getRedisAccessToken(request);
        if (redisAccessToken != null) {
            this.setFieldValByName(Consts.CREATE_USER, redisAccessToken.getUserId(), metaObject);
            this.setFieldValByName(Consts.UPDATE_USER, redisAccessToken.getUserId(), metaObject);
        }
        this.setFieldValByName(Consts.CREATE_TIME, System.currentTimeMillis(), metaObject);
        this.setFieldValByName(Consts.UPDATE_TIME, System.currentTimeMillis(), metaObject);
    }

    /**
     * 更新记录自动填充字段
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        RedisAccessToken redisAccessToken = this.getRedisAccessToken(request);
        if (redisAccessToken != null) {
            this.setFieldValByName(Consts.UPDATE_USER, redisAccessToken.getUserId(), metaObject);
        }
        this.setFieldValByName(Consts.UPDATE_TIME, System.currentTimeMillis(), metaObject);
    }

    /**
     * 根据request中用户名获取RedisAccessToken
     * @param request
     * @return
     */
    private RedisAccessToken getRedisAccessToken(HttpServletRequest request) {
        if (ObjectUtils.isNotNull(request.getUserPrincipal())) {
            String username = request.getUserPrincipal().getName();
            if (ObjectUtils.isNotNull(username)) {
                return redisService.get(username, RedisAccessToken.class);
            }
        }

        return null;
    }
}
