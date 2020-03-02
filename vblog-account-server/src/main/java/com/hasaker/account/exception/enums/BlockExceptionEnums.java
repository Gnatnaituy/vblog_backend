package com.hasaker.account.exception.enums;

import com.hasaker.common.exception.base.IExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.account.exception.enums
 * @author 余天堂
 * @create 2020/3/2 11:09
 * @description BlockExceptionEnums
 */
@Getter
@AllArgsConstructor
public enum BlockExceptionEnums implements IExceptionAssert {

    BLOCK_NOT_EXISTS("BLOCK_EXCEPTION_001", "Block not exists");

    private String code;
    private String message;
}
