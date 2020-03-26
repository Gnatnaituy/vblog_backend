package com.hasaker.face.service.user;

import com.hasaker.common.vo.PageInfo;
import com.hasaker.face.vo.request.RequestFriendPageVo;
import com.hasaker.face.vo.response.ResponseFriendInfoVo;

/**
 * @package com.hasaker.face.service
 * @author 余天堂
 * @create 2020/3/26 16:24
 * @description FriendService
 */
public interface FriendService {

    PageInfo<ResponseFriendInfoVo> list(RequestFriendPageVo pageVo);
}
