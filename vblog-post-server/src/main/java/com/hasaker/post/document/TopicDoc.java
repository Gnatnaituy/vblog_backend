package com.hasaker.post.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @package com.hasaker.post.document
 * @author 余天堂
 * @create 2020/3/24 18:18
 * @description TopicDoc
 */
@Data
@Document(indexName = "vblog-post-topic")
public class TopicDoc {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String createUser;

    @Field(type = FieldType.Date)
    private Date createTime;

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
}
