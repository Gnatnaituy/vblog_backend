package com.hasaker.vblog.entity;

import com.hasaker.vblog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 余天堂
 * @since 2019/11/3 18:02
 * @description 话题类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends BaseEntity {

    private static final long serialVersionUID = 1566694921915328948L;

    private String topicName;

    private String topicLogo;

    private String topicType;

    private String topicDesc;
}
