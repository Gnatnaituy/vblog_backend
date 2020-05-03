package com.hasaker.face.service.post;

import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.face.vo.response.message.ResponseMessageVoteVo;

import java.util.List;

/**
 * @package com.hasaker.face.service.post
 * @author 余天堂
 * @create 2020/3/28 02:03
 * @description VoteService
 */
public interface VoteService {

    List<ResponseUserInfoVo> list(Long postId);

    List<ResponseMessageVoteVo> listMessage(Long userId);
}
