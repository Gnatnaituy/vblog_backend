package com.hasaker.post.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @package com.hasaker.post.document
 * @author 余天堂
 * @create 2020/3/24 18:21
 * @description VoteDoc
 */
@Data
@Document(indexName = "vblog-post-vote")
public class VoteDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Long)
    private Long commentId;

    @Field(type = FieldType.Long)
    private Long voter;

    @Field(type = FieldType.Date)
    private Long voteTime;

    public static final String VOTER = "voter";
    public static final String VOTE_TIME = "voteTime";
}
