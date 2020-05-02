package com.hasaker.face.service.post.impl;

import cn.hutool.core.convert.Convert;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.service.post.TopicService;
import com.hasaker.face.vo.response.ResponseTopicDetailVo;
import com.hasaker.face.vo.response.ResponseUserInfoVo;
import com.hasaker.post.document.TopicDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/5/3 02:50
 * @description TopicServiceImpl
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private EsService esService;

    /**
     * Obtain topic detail by topicId
     * @param topicId
     * @return
     */
    @Override
    public ResponseTopicDetailVo detail(Long topicId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(topicId);

        TopicDoc topicDoc = esService.getById(topicId, TopicDoc.class);
        UserDoc userDoc = esService.getById(topicDoc.getCreateUser(), UserDoc.class);

        ResponseTopicDetailVo topicDetailVo = Convert.convert(ResponseTopicDetailVo.class, topicDoc);
        topicDetailVo.setCreateUser(Convert.convert(ResponseUserInfoVo.class, userDoc));

        return topicDetailVo;
    }
}
