package com.hasaker.face.vo.request;

import lombok.Data;

import java.util.List;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/5/3 21:15
 * @description RequestMessageReadVo
 */
@Data
public class RequestMessageReadVo {

    private List<Long> messageIds;

    private String messageType;
}
