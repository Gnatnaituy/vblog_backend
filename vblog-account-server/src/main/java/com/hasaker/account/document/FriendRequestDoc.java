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
    private String id;

    @Field(type = FieldType.Keyword)
    private String senderId;

    @Field(type = FieldType.Keyword)
    private String receiverId;

    @Field(type = FieldType.Keyword)
    private String remark;

    @Field(type = FieldType.Keyword)
    private String visibility;

    @Field(type = FieldType.Keyword)
    private String requestStatus;

    @Field(type = FieldType.Date)
    private String createTime;

    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String REMARK = "remark";
    public static final String VISIBILITY = "visibility";
    public static final String REQUEST_STATUS = "requestStatus";
}
