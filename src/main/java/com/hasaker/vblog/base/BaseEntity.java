package com.hasaker.vblog.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 余天堂
 * @since 2019/10/31 17:13
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6023771566281922919L;

    /**
     * 全局唯一ID
     */
    private String id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否被删除
     */
    private String isDeleted;

    /**
     * 版本
     */
    private Integer version;
}
