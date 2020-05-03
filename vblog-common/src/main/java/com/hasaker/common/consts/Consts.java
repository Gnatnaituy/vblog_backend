package com.hasaker.common.consts;

/**
 * @author 余天堂
 * @since 2019/10/31 17:40
 */
public final class Consts {

    // 数据库字段名
    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String CREATE_USER = "create_user";
    public static final String UPDATE_TIME = "update_time";
    public static final String UPDATE_USER = "update_user";
    public static final String IS_ENABLED = "is_enabled";
    public static final String IS_DELETED = "is_deleted";
    public static final String VERSION = "version";
    // ES字段名
    public static final String USER_ID = "userId";
    public static final String POST_ID = "postId";
    public static final String COMMENT_ID = "commentId";

    // 布尔类型
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    public static final Integer TRUE = 1;
    public static final Integer FALSE = 0;
    public static final Integer YES = 1;
    public static final Integer NO = 0;

    // 朋友圈可见性
    public static final Integer SECRET = 1;
    public static final Integer FRIEND = 2;
    public static final Integer OPEN = 3;

    // Password max & min length
    public static final Integer PASSWORD_MIN_LEN = 8;
    public static final Integer PASSWORD_MAX_LEN = 16;

    // redis key prefixes
    public static final String REDIS_FRIEND_ADD_REQUEST = "FRIEND:ADD:REQUEST:";
    public static final String REDIS_USER_LOGIN = "USER:LOGIN:";

    // OSS directory
    public static final String USER_AVATAR = "VBLOG/user_avatar/";
    public static final String USER_BACKGROUND = "VBLOG/user_background/";
    public static final String TOPIC_BACKGROUND = "VBLOG/topic_background/";
    public static final String POST_IMAGE = "VBLOG/post_image/";
    public static final String DEFAULT_PNG = "DEFAULT.png";
    // OSS default file
    public static final String DEFAULT_AVATAR = USER_AVATAR + DEFAULT_PNG;
    public static final String DEFAULT_USER_BACKGROUND = USER_BACKGROUND + DEFAULT_PNG;
    public static final String DEFAULT_TOPIC_BACKGROUND = TOPIC_BACKGROUND + DEFAULT_PNG;
    public static final String TOPIC_NO_DESC = "该话题的创建者还未添加描述信息";
}
