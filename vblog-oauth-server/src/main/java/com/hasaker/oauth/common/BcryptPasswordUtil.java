package com.hasaker.oauth.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @package com.hasaker.oauth
 * @author 余天堂
 * @create 2020/2/18 11:51
 * @description BcryptPasswordUtil
 */
public class BcryptPasswordUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bcryptPasswordEncoder.encode("5523"));
    }
}
