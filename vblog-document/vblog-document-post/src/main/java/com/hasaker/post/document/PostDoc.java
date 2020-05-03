package com.hasaker.post.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

/**
 * @author 余天堂
 * @since 2019/11/3 17:42
 * @description
 */
@Data
@Document(indexName = "vblog-post")
public class PostDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart", fielddata = true)
    private String content;

    @Field(type = FieldType.Integer)
    private Integer visibility;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Long)
    private List<Long> topics;

    @Field(type = FieldType.Long)
    private Long poster;

    @Field(type = FieldType.Date)
    private Long postTime;

    public static final String CONTENT = "content";
    public static final String VISIBILITY = "visibility";
    public static final String LOCATION = "location";
    public static final String TOPICS = "topics";
    public static final String POSTER = "poster";
    public static final String POST_TIME = "postTime";
}
