package com.hasaker.vblog.common;
/**
 * @author 余天堂
 * @since 2019/11/15 17:27
 * @description 
 */
public class MessageConsts {

    /**
     * 对象强行转换异常
     */
    public static final String CAST_EXCEPTION = "info.cast.exception";
    public static final String ERROR_SNOWFLAKE_SIZE = "info.error.snowflake.size.exception";


    /** 调用成功 */
    public static final String SUCCESS = "info.response.success";
    /** 调用失败  */
    public static final String ERROR = "info.response.error";
    /** 调用异常  */
    public static final String EXCEPTION = "info.response.exception";
    /** 操作失败 */
    public static final String OPERATION_FAILED = "info.operation.failed";


    /** 非法字符 */
    public static final String SQL_KEYWORD = "info.security.keyword.sql";
    /** 不支持的请求方式 */
    public static final String NO_SUPPORT = "info.support.exception";
    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "user.login.success";
    /** 退出成功 */
    public static final String LOGOUT_SUCCESS = "user.logout.success";
    /** 业务流程处理中发现核心处理对象为空 */
    public static final String ENTITY_EMPTY = "info.entity.empty";
    /** 参数为空 */
    public static final String ARG_ENTITY_EMPTY="info.arg.entity.empty";
}
