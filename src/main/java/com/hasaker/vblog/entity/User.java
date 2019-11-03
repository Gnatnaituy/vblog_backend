package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 余天堂
 * @since 2019/10/31 22:43
 * 用户实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String account;

    private String password;

    private String avatar;

    private String background;

    private String nickname;

    private String realname;

    private String gender;

    private Integer age;

    private String bio;

    private String region;

    private String location;

    private Date registerTime;
}
