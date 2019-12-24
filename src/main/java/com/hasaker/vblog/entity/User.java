package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.*;

/**
 * @author 余天堂
 * @since 2019/10/31 22:43
 * @description 用户实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    // 用户账号/用户名
    private String account;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 真实姓名
    private String realname;

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
