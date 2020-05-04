package com.hasaker.face.service.user;

import com.hasaker.common.vo.PageInfo;
import com.hasaker.face.vo.request.RequestUserSearchVo;
import com.hasaker.face.vo.response.ResponseUserDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/3 18:08
 * @description UserService
 */
public interface UserService {

    PageInfo<ResponseUserInfoVo> search(RequestUserSearchVo searchVo);

    ResponseUserDetailVo detail(Long userId, Long loggedUserId);

    Map<Long, ResponseUserInfoVo> mapUserInfo(Collection<Long> userIds);

    List<ResponseUserInfoVo> listUserInfo(Collection<Long> userIds);
}
