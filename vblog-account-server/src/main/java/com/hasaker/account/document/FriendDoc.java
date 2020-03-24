package com.hasaker.account.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

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
    private String id;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Keyword)
    private String friendId;

    @Field(type = FieldType.Keyword)
    private String remark;

    @Field(type = FieldType.Keyword)
    private String visibility;

    @Field(type = FieldType.Date)
    private Date createTime;

    public static final String REMARK = "remark";
    public static final String VISIBILITY = "visibility";
}
