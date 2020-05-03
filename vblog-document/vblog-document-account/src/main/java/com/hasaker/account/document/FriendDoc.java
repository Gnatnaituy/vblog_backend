package com.hasaker.account.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @package com.hasaker.account.document
 * @author 余天堂
 * @create 2020/3/24 23:57
 * @description FriendDoc
 */
@Data
@Document(indexName = "vblog-user-friend")
public class FriendDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Long)
    private Long friendId;

    @Field(type = FieldType.Keyword)
    private String remark;

    @Field(type = FieldType.Integer)
    private Integer visibility;

    @Field(type = FieldType.Date)
    private Long addTime;

    public static final String REMARK = "remark";
    public static final String VISIBILITY = "visibility";
    public static final String ADD_TIME = "addTime";
}
