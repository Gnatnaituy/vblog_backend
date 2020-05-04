package com.hasaker.post.message;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @package com.hasaker.post.message
 * @author 余天堂
 * @create 2020/5/3 20:43
 * @description CommentMessage
 */
@Data
@Document(indexName = "vblog-message-comment")
public class CommentMessageDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Long)
    private Long commentId;

    @Field(type = FieldType.Keyword)
    private String commentSummary;

    @Field(type = FieldType.Long)
    private Long receiver;

    @Field(type = FieldType.Long)
    private Long createUser;

    @Field(type = FieldType.Long)
    private Long createTime;

    @Field(type = FieldType.Integer)
    private Integer status;

    public static final String CREATE_USER = "createUser";
    public static final String RECEIVER = "receiver";
    public static final String STATUS = "status";
}
