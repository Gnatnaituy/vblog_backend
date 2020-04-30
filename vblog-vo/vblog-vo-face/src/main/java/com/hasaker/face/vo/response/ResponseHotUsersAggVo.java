package com.hasaker.face.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/5/1 01:52
 * @description ResponseHotUsersAggVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHotUsersAggVo {

    private ResponseUserInfoVo user;

    private Long count;
}
