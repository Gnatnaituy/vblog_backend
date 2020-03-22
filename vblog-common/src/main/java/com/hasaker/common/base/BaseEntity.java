package com.hasaker.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 余天堂
 * @since 2019/10/31 17:13
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6023771566281922919L;

    /**
     * 全局唯一ID(雪花算法生成)
     */
    private Long id;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    /**
     * 是否被删除(默认值0)
     */
    private Integer isDeleted;

    /**
     * 版本(默认值0)
     */
    @Version
    private Integer version;
}
