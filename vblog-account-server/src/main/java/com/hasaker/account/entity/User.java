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

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String NICKNAME = "nickname";
    private static final String AVATAR = "avatar";
    private static final String GENDER = "gender";
    private static final String AGE = "age";
    private static final String BIO = "bio";
    private static final String COUNTRY = "country";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String STATUS = "status";
}
