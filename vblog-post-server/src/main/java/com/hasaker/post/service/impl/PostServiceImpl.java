package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.post.entity.*;
import com.hasaker.post.exception.enums.PostExceptionEnum;
import com.hasaker.post.mapper.PostMapper;
import com.hasaker.post.service.*;
import com.hasaker.post.vo.request.RequestPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description PostServiceImpl
 */
@Service
public class PostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    private PostImageService postImageService;
    @Autowired
    private PostTopicService postTopicService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private VoteService voteService;

    /**
     * Create a new post
     * @param postVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(RequestPostVo postVo) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postVo);

        Post post = Convert.convert(Post.class, postVo);
        post = this.saveId(post);
        final Long postId = post.getId();

        // Save images of this post
        if (ObjectUtils.isNotNull(postVo.getImages())) {
            List<PostImage> images = postVo.getImages().stream()
                    .map(o -> Convert.convert(PostImage.class, o))
                    .collect(Collectors.toList());
            images.forEach(o -> o.setPostId(postId));
            images.forEach(o -> postImageService.save(o));
        }

        // Save topics of this post
        if (ObjectUtils.isNotNull(postVo.getTopics())) {
            postVo.getTopics().stream().filter(o -> ObjectUtils.isNull(o.getTopicId()))
                    .forEach(o -> {
                        Topic topic = Convert.convert(Topic.class, o);
                        o.setTopicId(topicService.saveId(topic).getId());
                    });

            List<PostTopic> topics = postVo.getTopics().stream()
                    .map(o -> Convert.convert(PostTopic.class, o))
                    .collect(Collectors.toList());
            topics.forEach(o -> o.setPostId(postId));
            topics.forEach(o -> postTopicService.save(o));
        }
    }

    /**
     * Delete a post by ID
     * Delete related images, topics, comments and votes
     * @param postId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long postId) {
        CommonExceptionEnums.NOT_NULL_ARG.assertNotEmpty(postId);

        Post post = this.getById(postId);
        PostExceptionEnum.POST_NOT_EXISTS.assertNotEmpty(post);
        this.removeById(postId);

        // Delete images belong to this post
        QueryWrapper<PostImage> postImageQueryWrapper = new QueryWrapper<>();
        postImageQueryWrapper.eq(PostImage.POST_ID, postId);
        postImageService.remove(postImageQueryWrapper);

        // Delete topics belong to this post
        QueryWrapper<PostTopic> postTopicQueryWrapper = new QueryWrapper<>();
        postTopicQueryWrapper.eq(PostTopic.POST_ID, postId);
        postTopicService.remove(postTopicQueryWrapper);

        // Delete comments belong to this post
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq(Comment.POST_ID, postId);
        commentService.remove(commentQueryWrapper);

        // Delete votes belong to this post
        QueryWrapper<Vote> voteQueryWrapper = new QueryWrapper<>();
        voteQueryWrapper.eq(Vote.POST_ID, postId);
        voteService.remove(voteQueryWrapper);
    }
}
