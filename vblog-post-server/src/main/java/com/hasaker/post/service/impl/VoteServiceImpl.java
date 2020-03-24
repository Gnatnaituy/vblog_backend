package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.post.document.VoteDoc;
import com.hasaker.post.entity.Vote;
import com.hasaker.post.mapper.VoteMapper;
import com.hasaker.post.service.VoteService;
import com.hasaker.post.vo.request.RequestVoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description VoteServiceImpl
 */
@Service
public class VoteServiceImpl extends BaseServiceImpl<VoteMapper, Vote> implements VoteService {

    @Autowired
    private EsService esService;

    /**
     * Vote a post or a comment
     * @param voteVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void vote(RequestVoteVo voteVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(voteVo);

        Vote vote = Convert.convert(Vote.class, voteVo);
        vote.setIsDownvote(false);
        vote = this.saveId(vote);

        VoteDoc voteDoc = new VoteDoc();
        voteDoc.setId(String.valueOf(vote.getId()));
        voteDoc.setPostId(String.valueOf(vote.getPostId()));
        voteDoc.setCommentId(String.valueOf(vote.getCommentId()));
        voteDoc.setIsDownvote(vote.getIsDownvote());
        esService.index(voteDoc);
    }
}
