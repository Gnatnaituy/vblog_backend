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
 * @create 2020/3/23 20:48
 * @description ImageDoc
 */
@Data
@Document(indexName = "vblog-post-image")
public class ImageDoc {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String postId;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Integer)
    private Integer sort;

    @Field(type = FieldType.Keyword)
    private String createUser;

    @Field(type = FieldType.Date)
    private Date createTime;

    public static final String URL = "url";
    public static final String SORT = "sort";
}
