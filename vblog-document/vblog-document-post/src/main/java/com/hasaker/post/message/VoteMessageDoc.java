package com.hasaker.post.message;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @package com.hasaker.post.document
 * @author 余天堂
 * @create 2020/5/3 20:42
 * @description VoteMessageDoc
 */
@Data
@Document(indexName = "vblog-message-vote")
public class VoteMessageDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Keyword)
    private String postSummary;

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
