package com.hasaker.post.service;

import com.hasaker.common.base.BaseService;
import com.hasaker.post.entity.Post;
import com.hasaker.post.vo.request.RequestPostVo;

/**
 * @package com.hasaker.post.service
 * @author 余天堂
 * @create 2020/3/22 19:40
 * @description PostService
 */
public interface PostService extends BaseService<Post> {

    Long post(RequestPostVo postVo);

    void delete(Long postId);

    void indexAllPosts();
}
