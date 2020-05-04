package com.hasaker.common.consts;

/**
 * @author 余天堂
 * @since 2019/10/31 17:40
 */
public final class Consts {

    // MySQL common filed
    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String CREATE_USER = "create_user";
    public static final String UPDATE_TIME = "update_time";
    public static final String UPDATE_USER = "update_user";
    public static final String IS_ENABLED = "is_enabled";
    public static final String IS_DELETED = "is_deleted";
    public static final String VERSION = "version";

    // ES common filed
    public static final String USER_ID = "userId";
    public static final String POST_ID = "postId";
    public static final String COMMENT_ID = "commentId";

    // Boolean consts
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    public static final Integer YES = 1;
    public static final Integer NO = 0;

    // Post visibility
    public static final Integer POST_SECRET = 1;
    public static final Integer POST_FRIEND = 2;
    public static final Integer POST_OPEN = 3;
    // Friend visibility
    public static final Integer FRIEND_BOTH = 1;
    public static final Integer FRIEND_NOT_FRIEND = 2;
    public static final Integer FRIEND_NOT_ME = 3;
    public static final Integer FRIEND_NOT_BOTH = 4;

    // Friend status
    public static final String NOT_FRIEND = "NOT_FRIEND";
    public static final String REQUEST_SEND = "REQUEST_SEND";
    public static final String REQUEST_DENIED = "REQUEST_DENIED";
    public static final String IS_FRIEND = "IS_FRIEND";

    // Message type & status
    public static final String MESSATE_TYPE_VOTE = "VOTE";
    public static final String MESSATE_TYPE_COMMENT = "COMMENT";
    public static final Integer MESSAGE_STATUS_UNREAD = 0;
    public static final Integer MESSAGE_STATUS_READ = 1;

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
