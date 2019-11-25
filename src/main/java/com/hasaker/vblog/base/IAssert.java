package com.hasaker.vblog.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author 余天堂
 * @since 2019/11/15 09:43
 * @description 
 */
public interface IAssert {

    /**
     * @return
     */
    BaseException newException();

    /**
     * 创建异常
     * @param args
     * @return
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     * @param t
     * @param args
     * @return
     */
    BaseException newException(Throwable t, Object... args);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException(obj);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param obj 待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(null, args);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotEmpty(Object obj) {
        if (obj == null) {
            throw newException(obj);
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            throw newException(obj);
        }
        if (obj instanceof CharSequence && ((CharSequence) obj).length() == 0) {
            throw newException(obj);
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            throw newException(obj);
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            throw newException(obj);
        }
    }

    /**
     * @param condition
     */
    default void isTrue(boolean condition, Object... args) {
        if (condition) {
            throw newException(args);
        }
    }

    /**
     * @param condition
     */
    default void isFalse(boolean condition, Object... args) {
        if (!condition) {
            throw newException(args);
        }
    }

    /**
     * @param condition
     */
    default void isTrue(boolean condition) {
        if (condition) {
            throw newException();
        }
    }

    /**
     * @param condition
     */
    default void isFalse(boolean condition) {
        if (!condition) {
            throw newException();
        }
    }
}
