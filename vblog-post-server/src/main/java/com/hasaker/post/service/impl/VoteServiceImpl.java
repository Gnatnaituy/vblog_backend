package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.post.document.VoteDoc;
import com.hasaker.post.entity.Vote;
import com.hasaker.post.exception.enums.PostExceptionEnum;
import com.hasaker.post.mapper.VoteMapper;
import com.hasaker.post.message.VoteMessageDoc;
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

        QueryWrapper<Vote> voteQueryWrapper = new QueryWrapper<>();
        voteQueryWrapper.eq(Consts.CREATE_USER, voteVo.getVoter());
        if (ObjectUtils.isNotNull(voteVo.getPostId())) {
            voteQueryWrapper.eq(Vote.POST_ID, voteVo.getPostId());
        }
        if (ObjectUtils.isNotNull(voteVo.getCommentId())) {
            voteQueryWrapper.eq(Vote.COMMENT_ID, voteVo.getCommentId());
        }
        Vote vote = this.getOne(voteQueryWrapper);

        if (voteVo.getDisvote()) {
            PostExceptionEnum.VOTE_NOT_EXISTS.assertNotEmpty(vote);

            // Remove vote from MySQL
            this.removeById(vote.getId());

            // Remove vote from Elasticsearch
            esService.delete(vote.getId(), VoteDoc.class);
        } else {
            PostExceptionEnum.VOTE_ALREADY_EXISTS.assertEmpty(vote);

            // Save vote to MySQL
            vote = Convert.convert(Vote.class, voteVo);
            vote = this.saveId(vote);
            vote = this.getById(vote.getId());

            // Save vote to ES
            VoteDoc voteDoc = Convert.convert(VoteDoc.class, vote);
            voteDoc.setVoter(vote.getCreateUser());
            voteDoc.setVoteTime(vote.getCreateTime());
            esService.index(voteDoc);

            // Save vote message to ES
            VoteMessageDoc voteMessageDoc = Convert.convert(VoteMessageDoc.class, vote);
            voteMessageDoc.setReceiver(vote.getCreateUser());
            voteMessageDoc.setStatus(Consts.MESSAGE_STATUS_UNREAD);
            esService.index(voteMessageDoc);
        }
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

        esService.index(voteDocs);
    }
}
