package com.hasaker.post.service;

import com.hasaker.common.base.BaseService;
import com.hasaker.post.entity.Vote;
import com.hasaker.post.vo.request.RequestVoteVo;

/**
 * @package com.hasaker.post.service
 * @author 余天堂
 * @create 2020/3/22 19:41
 * @description VoteService
 */
public interface VoteService extends BaseService<Vote> {

    void vote(RequestVoteVo voteVo);

    void indexAllVotes();
}
