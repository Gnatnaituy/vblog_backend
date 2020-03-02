package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.account.entity
 * @author 余天堂
 * @create 2020/3/2 10:45
 * @description Block
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block extends BaseEntity {

    // 用户ID
    private Long userId;

    // 屏蔽对象ID
    private Long blockUserId;

    public static final String USER_ID = "user_id";
    public static final String BLOCK_USER_ID = "block_user_id";
}
