package com.hasaker.account.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.Set;

/**
 * @author 余天堂
 * @since 2019/11/3 18:21
 * @description
 */
@Data
@Document(indexName = "vblog-user")
public class UserDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String phone;

    @Field(type = FieldType.Keyword)
    private String nickname;

    @Field(type = FieldType.Keyword)
    private String gender;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private String avatar;

    @Field(type = FieldType.Keyword)
    private String background;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", fielddata = true)
    private String bio;

    @Field(type = FieldType.Keyword)
    private String country;

    @Field(type = FieldType.Keyword)
    private String province;

    @Field(type = FieldType.Keyword)
    private String city;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Long)
    private Set<Long> blocks;

    @Field(type = FieldType.Date)
    private Long registerTime;

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String NICKNAME = "nickname";
    public static final String AVATAR = "avatar";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String BIO = "bio";
    public static final String COUNTRY = "country";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String LOCATION = "location";
    public static final String BLOCKS = "blocks";
    public static final String REGISTER_TIME = "registerTime";
}
