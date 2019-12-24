package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * @author 余天堂
 * @since 2019/10/31 17:00
 * @description 动态实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    private static final long serialVersionUID = 7086891429068468891L;

    private Long topicId;

    private String content;

    private Integer visibility;

    private Long postUserId;

    private Date postTime;
}
