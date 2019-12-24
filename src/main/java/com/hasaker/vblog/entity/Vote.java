package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * @author 余天堂
 * @since 2019/10/31 22:52
 * @description 点赞实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote extends BaseEntity {

    private static final long serialVersionUID = -5842088763229665281L;

    private Long postId;

    private Long voteUserId;

    private Date voteTime;
}
