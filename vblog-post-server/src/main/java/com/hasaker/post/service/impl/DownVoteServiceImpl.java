package com.hasaker.post.service.impl;

import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.post.entity.DownVote;
import com.hasaker.post.mapper.DownVoteMapper;
import com.hasaker.post.service.DownVoteService;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description DownVoteServiceImpl
 */
@Service
public class DownVoteServiceImpl extends BaseServiceImpl<DownVoteMapper, DownVote> implements DownVoteService {
}
