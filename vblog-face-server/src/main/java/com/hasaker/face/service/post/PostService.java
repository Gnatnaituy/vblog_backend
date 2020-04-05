package com.hasaker.face.service.post;

import com.hasaker.common.vo.PageInfo;
import com.hasaker.face.vo.request.RequestPostSearchVo;
import com.hasaker.face.vo.response.ResponsePostVo;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/4 11:24
 * @description PostService
 */
public interface PostService {

    PageInfo<ResponsePostVo> page(RequestPostSearchVo pageVo);

    void indexAll();
}
