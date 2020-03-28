package com.hasaker.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hasaker.common.consts.RequestConsts;
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

    private static final String CREATE_USER = "createUser";
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_USER = "updateUser";
    private static final String UPDATE_TIME = "updateTime";

    /**
     * Auto fill fields when insert record
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        RedisAccessToken redisAccessToken = this.getRedisAccessToken();
        if (redisAccessToken != null) {
            this.setFieldValByName(CREATE_USER, redisAccessToken.getUserId(), metaObject);
            this.setFieldValByName(UPDATE_USER, redisAccessToken.getUserId(), metaObject);
        }
        this.setFieldValByName(CREATE_TIME, System.currentTimeMillis(), metaObject);
        this.setFieldValByName(UPDATE_TIME, System.currentTimeMillis(), metaObject);
    }

    /**
     * Auto fill fields when update record
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        RedisAccessToken redisAccessToken = this.getRedisAccessToken();
        if (redisAccessToken != null) {
            this.setFieldValByName(UPDATE_USER, redisAccessToken.getUserId(), metaObject);
        }
        this.setFieldValByName(UPDATE_TIME, System.currentTimeMillis(), metaObject);
    }

    /**
     * Obtain RedisAccessToken by username in request's JWT Token
     * @return
     */
    private RedisAccessToken getRedisAccessToken() {
        String userAccount = request.getHeader(RequestConsts.USER_ACCOUNT);
        if (userAccount != null) {
            return redisService.get(userAccount, RedisAccessToken.class);
        }

        return null;
    }
}
