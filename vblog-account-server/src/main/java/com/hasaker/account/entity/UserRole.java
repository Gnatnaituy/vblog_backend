package com.hasaker.account.entity;

import com.hasaker.common.base.BaseEntity;
import lombok.*;

/**
 * @package com.hasaker.user.entity
 * @author 余天堂
 * @create 2020/2/11 11:52
 * @description UserRole
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseEntity {

    @NonNull
    private Long userId;

    @NonNull
    private Long roleId;
}
