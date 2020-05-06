package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.service.post.VoteService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.face.vo.response.message.ResponseMessageVoteVo;
import com.hasaker.post.document.VoteDoc;
import com.hasaker.post.message.VoteMessageDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/28 02:22
 * @description VoteServiceImpl
 */
@Service
@Slf4j
public class VoteServiceImpl implements VoteService {

    @Autowired
    private UserService userService;
    @Autowired
    private EsService esService;

    /**
     * List voters by postId
     * @param postId
     * @return
     */
    @Override
    public List<ResponseUserInfoVo> list(Long postId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postId);

        List<VoteDoc> voteDocs = esService.list(new Pair<>(Consts.POST_ID, postId), VoteDoc.class);
        if (ObjectUtils.isNotNull(voteDocs)) {
            return userService.listUserInfo(voteDocs.stream().map(VoteDoc::getVoter).collect(Collectors.toList()));
        }

        return Collections.emptyList();
    }

    /**
     * List unread vote message for current logged user
     * @param userId
     * @return
     */
    @Override
    public List<ResponseMessageVoteVo> listMessage(Long userId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(userId);

        List<Pair<String, Object>> matchFields = new ArrayList<>();
        matchFields.add(new Pair<>(VoteMessageDoc.RECEIVER, userId));
        matchFields.add(new Pair<>(VoteMessageDoc.STATUS, Consts.MESSAGE_STATUS_UNREAD));
        List<VoteMessageDoc> voteMessageDocs = esService.list(matchFields, VoteMessageDoc.class);

        if (ObjectUtils.isNotNull(voteMessageDocs)) {
            List<Long> voterIds = voteMessageDocs.stream()
                    .map(VoteMessageDoc::getCreateUser).collect(Collectors.toList());
            List<UserDoc> voters = esService.getByIds(voterIds, UserDoc.class);
            Map<Long, ResponseUserInfoVo> userMap = voters.stream()
                    .map(o -> Convert.convert(ResponseUserInfoVo.class, o))
                    .collect(Collectors.toMap(ResponseUserInfoVo::getId, o -> o));

            return voteMessageDocs.stream().filter(o -> !o.getCreateUser().equals(userId)).map(o -> {
                ResponseMessageVoteVo voteVo = Convert.convert(ResponseMessageVoteVo.class, o);
                voteVo.setCreateUser(userMap.get(o.getCreateUser()));
                return voteVo;
            }).sorted((o1, o2) -> (int) (o2.getCreateTime() - o1.getCreateTime())).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
