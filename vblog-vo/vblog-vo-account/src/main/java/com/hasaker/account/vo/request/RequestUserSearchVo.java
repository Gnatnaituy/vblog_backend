package com.hasaker.account.vo.request;

import lombok.Data;

/**
 * @package com.hasaker.account.vo.request
 * @author 余天堂
 * @create 2020/3/19 22:00
 * @description RequestUserSearchVo
 */
@Data
public class RequestUserSearchVo {

    private String keyword;

    private String gender;

    private Integer maxAge;

    private Integer minAge;

    private Long current;

    private Long size;
}
