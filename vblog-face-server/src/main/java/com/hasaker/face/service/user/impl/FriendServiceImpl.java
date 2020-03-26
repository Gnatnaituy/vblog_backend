package com.hasaker.face.service.user.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.account.document.FriendDoc;
import com.hasaker.account.document.UserDoc;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.PageInfo;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.face.service.user.FriendService;
import com.hasaker.face.vo.request.RequestFriendPageVo;
import com.hasaker.face.vo.response.ResponseFriendInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.face.service.post.impl
 * @author 余天堂
 * @create 2020/3/26 16:26
 * @description FriendServiceImpl
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private EsService esService;

    /**
     * List friends by userId
     * @param pageVo
     * @return
     */
    @Override
    public PageInfo<ResponseFriendInfoVo> list(RequestFriendPageVo pageVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(pageVo);

        Page<FriendDoc> friendDocs = esService.page(Consts.USER_ID, pageVo.getUserId(), FriendDoc.class);
        if (ObjectUtils.isNotNull(friendDocs)) {
            List<String> friendIds = friendDocs.stream().map(FriendDoc::getFriendId).collect(Collectors.toList());
            List<UserDoc> userDocs = esService.getByIds(friendIds, UserDoc.class);
            PageInfo<ResponseFriendInfoVo> friendPage = new PageInfo<>(friendDocs);
            Map<String, FriendDoc> friendDocMap = new HashMap<>(userDocs.size());
            friendDocs.getContent().forEach(o -> friendDocMap.put(o.getFriendId(), o));
            friendPage.setContent(userDocs.stream().map(user -> {
                ResponseFriendInfoVo friendInfoVo = Convert.convert(ResponseFriendInfoVo.class, user);
                friendInfoVo.setRemark(friendDocMap.get(user.getId()).getRemark());
                friendInfoVo.setVisibility(friendDocMap.get(user.getId()).getVisibility());
                return friendInfoVo;
            }).collect(Collectors.toList()));

            return friendPage;
        }

        return new PageInfo<>();
    }
}
