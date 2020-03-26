package com.hasaker.account.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @package com.hasaker.post.document
 * @author 余天堂
 * @create 2020/3/23 20:51
 * @description CommentDoc
 */
@Data
@Document(indexName = "vblog-post-comment")
public class CommentDoc {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String postId;

    @Field(type = FieldType.Keyword)
    private String commentId;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String createUser;

    @Field(type = FieldType.Date)
    private Date createTime;

    public static final String CONTENT = "content";
}
