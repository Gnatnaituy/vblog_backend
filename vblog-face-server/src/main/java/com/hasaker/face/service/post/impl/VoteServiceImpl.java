package com.hasaker.face.service.post.impl;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.service.post.VoteService;
import com.hasaker.face.service.user.UserService;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.VoteDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        return new ArrayList<>();
    }
}
