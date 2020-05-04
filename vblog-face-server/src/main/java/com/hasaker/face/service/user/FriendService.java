package com.hasaker.face.service.user;

import com.hasaker.common.vo.PageInfo;
import com.hasaker.face.vo.request.SearchVo;
import com.hasaker.face.vo.response.ResponseFriendInfoVo;
import com.hasaker.face.vo.response.ResponseFriendRequestVo;

import java.util.List;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/26 16:24
 * @description FriendService
 */
public interface FriendService {

    PageInfo<ResponseFriendInfoVo> list(SearchVo searchVo);

    List<ResponseFriendRequestVo> listFriendRequest(Long userId);
}
