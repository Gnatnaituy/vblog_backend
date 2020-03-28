package com.hasaker.post.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Integer)
    private Integer sort;

    @Field(type = FieldType.Long)
    private Long uploader;

    @Field(type = FieldType.Date)
    private Long uploadTime;

    public static final String URL = "url";
    public static final String SORT = "sort";
    public static final String UPLOADER = "uploader";
    public static final String UPLOAD_TIME = "uploadTime";
}
