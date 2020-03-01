package com.hasaker.common.exception.base;

import cn.hutool.core.util.ObjectUtil;

/**
 * @package com.hasaker.vblog.base
 * @author 余天堂
 * @create 2019/12/24 14:54
 * @description BaseAssert
 */
public interface IAssert {

    CommonException newException();

    default void assertNotEmpty(Object object) {
        if (ObjectUtil.isEmpty(object)) {
            throw newException();
        }
    }

    default void isTrue(boolean condition) {
        if (condition) {
            throw newException();
        }
    }

    default void isFalse(boolean condition) {
        if (!condition) {
            throw newException();
        }
    }
}
