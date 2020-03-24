package com.hasaker.post.service.impl;

import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.post.entity.PostImage;
import com.hasaker.post.mapper.PostImageMapper;
import com.hasaker.post.service.PostImageService;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description PostImageServiceImpl
 */
@Service
public class PostImageServiceImpl extends BaseServiceImpl<PostImageMapper, PostImage> implements PostImageService {
}
