package com.hasaker.account.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.Date;
import java.util.Set;

/**
 * @author 余天堂
 * @since 2019/11/3 17:42
 * @description
 */
@Data
@Document(indexName = "vblog-post")
public class PostDoc {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String visibility;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Keyword)
    private Set<String> topics;

    @Field(type = FieldType.Keyword)
    private String createUser;

    @Field(type = FieldType.Date)
    private Date createTime;

    public static final String CONTENT = "content";
    public static final String VISIBILITY = "visibility";
    public static final String LOCATION = "location";
    public static final String TOPICS = "topics";
}
