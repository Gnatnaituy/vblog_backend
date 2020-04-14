package com.hasaker.post.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hasaker.common.base.impl.BaseServiceImpl;
import com.hasaker.component.elasticsearch.service.EsService;
import com.hasaker.post.document.ImageDoc;
import com.hasaker.post.entity.PostImage;
import com.hasaker.post.mapper.PostImageMapper;
import com.hasaker.post.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @package com.hasaker.post.service.impl
 * @author 余天堂
 * @create 2020/3/22 19:43
 * @description PostImageServiceImpl
 */
@Service
public class PostImageServiceImpl extends BaseServiceImpl<PostImageMapper, PostImage> implements PostImageService {

    @Autowired
    private EsService esService;

    @Override
    public void indexAllPostImages() {
        QueryWrapper<PostImage> queryWrapper = new QueryWrapper<>();
        List<PostImage> postImages = this.list(queryWrapper);

        if (ObjectUtils.isNotNull(postImages)) {
            List<ImageDoc> imageDocs = postImages.stream().map(o -> {
                ImageDoc imageDoc = Convert.convert(ImageDoc.class, o);
                imageDoc.setUploader(o.getCreateUser());
                imageDoc.setUploadTime(o.getCreateTime());
                return imageDoc;
            }).collect(Collectors.toList());

            esService.index(imageDocs);
        }
    }
}
