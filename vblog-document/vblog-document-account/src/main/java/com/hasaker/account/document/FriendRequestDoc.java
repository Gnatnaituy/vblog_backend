package com.hasaker.account.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @package com.hasaker.account.document
 * @author 余天堂
 * @create 2020/3/25 01:00
 * @description FriendRequestDoc
 */
@Data
@Document(indexName = "vblog-user-friend-request")
public class FriendRequestDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long senderId;

    @Field(type = FieldType.Long)
    private Long receiverId;

    @Field(type = FieldType.Keyword)
    private String senderRemark;

    @Field(type = FieldType.Keyword)
    private String senderVisibility;

    @Field(type = FieldType.Keyword)
    private String requestStatus;

    @Field(type = FieldType.Date)
    private Long sendTime;

    @Field(type = FieldType.Date)
    private Long acceptTime;

    @Field(type = FieldType.Date)
    private Long denyTime;

    @Field(type = FieldType.Date)
    private Long ignoreTime;

    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String REMARK = "remark";
    public static final String VISIBILITY = "visibility";
    public static final String REQUEST_STATUS = "requestStatus";
    public static final String SEND_TIME = "sendTime";
    public static final String ACCEPT_TIME = "acceptTime";
    public static final String DENY_TIME = "denyTime";
    public static final String IGNORE_TIME = "ignoreTime";
}
