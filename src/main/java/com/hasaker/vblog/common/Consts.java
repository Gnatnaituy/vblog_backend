package com.hasaker.vblog.common;

/**
 * @author 余天堂
 * @since 2019/10/31 17:40
 */
public final class Consts {

    public static final String EMPTY = "";

    // 常用状态
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    public static final String ERROR = "0";
    public static final String DISABLED = "0";
    public static final String ENABLED = "1";

    // 数据库字段名
    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String CREATE_USER = "create_user";
    public static final String UPDATE_TIME = "update_time";
    public static final String UPDATE_USER = "update_user";
    public static final String IS_DELETED = "is_deleted";
    public static final String VERSION = "version";

    // 逻辑删除标识符为0则该信息未删除,1为已删除
    public static final Integer UNDELETED = 0;
    public static final Integer DELETED = 1;

    // YN类型
    public static final Integer NO = 0;
    public static final Integer YES = 1;

    // 布尔类型
    public static final Integer FALSE = 0;
    public static final Integer TRUE = 1;

    // 朋友圈可见性
    public static final Integer SECRET = 1;
    public static final Integer FRIEND = 2;
    public static final Integer OPEN = 3;

    // redis-key-前缀-shiro:cache:
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";
    // redis-key-前缀-shiro:access_token:
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";
    // redis-key-前缀-shiro:refresh_token:
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    // JWT-account:
    public static final String ACCOUNT = "account";
    // JWT-currentTimeMillis:
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";
    // Password max length
    public static final Integer PASSWORD_MAX_LEN = 8;
}
