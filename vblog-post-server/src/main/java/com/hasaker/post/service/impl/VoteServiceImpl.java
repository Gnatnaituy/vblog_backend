package com.hasaker.post.service.impl;

import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.post.entity.Vote;
import com.hasaker.post.mapper.VoteMapper;
import com.hasaker.post.service.VoteService;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description VoteServiceImpl
 */
@Service
public class VoteServiceImpl extends BaseServiceImpl<VoteMapper, Vote> implements VoteService {
}
