package com.hasaker.face.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/4/30 22:21
 * @description ResponseHotTopicsAggVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHotTopicsAggVo {

    private ResponseTopicInfoVo topic;

    private Long count;
}
