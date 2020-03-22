package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 余天堂
 * @since 2019/10/31 22:43
 * @description 用户实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    // 用户账号/用户名
    private String username;

    // 邮箱
    private String email;

    // 手机
    private String phone;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 头像
    private String avatar;

    // 性别
    private String gender;

    // 年龄
    private Integer age;

    // 签名
    private String bio;

    // 国家
    private String country;

    // 省
    private String province;

    // 市
    private String city;

    // 用户状态
    private String status;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
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
    public static final String STATUS = "status";
}
