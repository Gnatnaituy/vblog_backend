package com.hasaker.common.exception.base;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.text.MessageFormat;

/**
 * @package com.hasaker.vblog.base
 * @author 余天堂
 * @create 2019/12/24 14:53
 * @description BaseExceptionAssert
 */
public interface BaseExceptionAssert extends BaseResponseEnum, BaseAssert {

    @Override
    default BaseException newException() {
        return new CommonException(this);
    }

    @Override
    default BaseException newException(Object... args) {
        String msg;
        if(StringUtils.isNotBlank(this.getMessage())) {
            msg = MessageFormat.format(this.getMessage(), args);
        } else {
            msg = this.getCode();
        }

        return new CommonException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        return new CommonException(this, args, null);
    }
}
