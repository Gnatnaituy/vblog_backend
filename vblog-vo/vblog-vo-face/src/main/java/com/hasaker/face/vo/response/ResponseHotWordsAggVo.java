package com.hasaker.face.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/4/30 01:46
 * @description ResponseBucketVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHotWordsAggVo {

    private String word;

    private Long count;
}
