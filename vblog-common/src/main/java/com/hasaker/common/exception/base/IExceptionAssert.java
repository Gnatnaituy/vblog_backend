package com.hasaker.common.exception.base;

import com.hasaker.common.base.IEnum;

/**
 * @package com.hasaker.vblog.base
 * @author 余天堂
 * @create 2019/12/24 14:53
 * @description BaseExceptionAssert
 */
public interface IExceptionAssert extends IEnum, IAssert {

    @Override
    default CommonException newException() {
        return new CommonException(this);
    }
}
