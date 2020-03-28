package com.hasaker.post.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Long)
    private Long commentId;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Long)
    private Long commenter;

    @Field(type = FieldType.Date)
    private Long commentTime;

    public static final String CONTENT = "content";
    public static final String COMMENTER = "commenter";
    public static final String COMMENT_TIME = "commentTime";
}
