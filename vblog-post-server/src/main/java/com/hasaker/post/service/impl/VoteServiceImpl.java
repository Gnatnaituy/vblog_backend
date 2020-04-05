package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.List;
import java.util.stream.Collectors;

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
        vote = this.saveId(vote);

        // Save vote to es
        VoteDoc voteDoc = Convert.convert(VoteDoc.class, vote);
        voteDoc.setVoter(vote.getCreateUser());
        voteDoc.setVoteTime(vote.getCreateTime());
        esService.index(voteDoc);
    }

    @Override
    public void indexAllVotes() {
        QueryWrapper<Vote> voteQueryWrapper = new QueryWrapper<>();
        List<Vote> votes = this.list(voteQueryWrapper);

        List<VoteDoc> voteDocs = votes.stream().map(o -> {
            VoteDoc voteDoc = Convert.convert(VoteDoc.class, o);
            voteDoc.setVoter(o.getCreateUser());
            voteDoc.setVoteTime(o.getCreateTime());
            return voteDoc;
        }).collect(Collectors.toList());

        esService.deleteIndex(VoteDoc.class);
        esService.index(voteDocs);
    }
}
