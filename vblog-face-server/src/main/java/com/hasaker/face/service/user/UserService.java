package com.hasaker.face.service.user;

import com.hasaker.common.vo.PageInfo;
import com.hasaker.face.vo.request.RequestUserSearchVo;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/3 18:08
 * @description UserService
 */
public interface UserService {

    PageInfo<ResponseUserInfoVo> search(RequestUserSearchVo searchVo);

    ResponseUserDetailVo detail(String userId);
}
