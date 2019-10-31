package com.hasaker.vblog.common;

/**
 * @author 余天堂
 * @since 2019/10/31 17:40
 */
public final class Consts {

    public static final String EMPTY = "";

    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    public static final String ERROR = "0";
    public static final String DISABLED = "0";
    public static final String ENABLED = "1";

    /** 数据库字段名 */
    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String CREATE_USER = "create_user";
    public static final String UPDATE_TIME = "update_time";
    public static final String UPDATE_USER = "update_user";
    public static final String IS_DELETED = "is_deleted";
    public static final String VERSION = "version";
    /** 逻辑删除标识符为0则该信息未删除,1为已删除 */
    public static final String UNDELETE = "0";
    public static final String DELETED = "1";
}
