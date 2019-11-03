package com.hasaker.vblog.document;

import java.util.Date;
import java.util.List;

/**
 * @author 余天堂
 * @since 2019/11/3 18:21
 * @description 
 */
public class UserDocument {

    private String userId;

    private String account;

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

    private List<PostDocument> posts;
}
