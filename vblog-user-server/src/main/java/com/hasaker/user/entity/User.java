package com.hasaker.user.entity;

import com.hasaker.user.base.BaseEntity;
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

    // 密码
    private String password;

    // 昵称
    private String nickname;

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
}
