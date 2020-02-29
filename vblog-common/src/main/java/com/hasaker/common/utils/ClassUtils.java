package com.hasaker.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @package com.hasaker.vblog.utils
 * @author 余天堂
 * @create 2019/12/26 23:46
 * @description ClassUtils
 */
public final class ClassUtils {

    public static <T> Class<T> getParameterizedType(Class<?> clazz) {
        return getParameterizedType(clazz, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getParameterizedType(Class<?> clazz, int index) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not have parameterized type.");
        }
        ParameterizedType pt = (ParameterizedType) type;

        Type[] types = pt.getActualTypeArguments();
        if (types.length <= index) {
            throw new IllegalArgumentException(
                    "Class " + clazz.getName() + " only has " + types.length + " parameterized types.");
        }

        Type r = types[index];
        if (!(r instanceof Class<?>)) {
            throw new IllegalArgumentException(
                    "Class " + clazz.getName() + " does not have parameterized type of class.");
        }

        return (Class<T>) r;
    }
}
