package com.hasaker.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @package com.hasaker.common.utils
 * @author 余天堂
 * @create 2020/3/27 15:04
 * @description BeanUtils
 */
public class BeanUtils {

    // Get the class of the generic
    public static  <T> Class<T> getClass(T bean) {
        Type[] types = bean.getClass().getGenericInterfaces();
        Type[] params = ((ParameterizedType) types[0]).getActualTypeArguments();

        return (Class) params[0];
    }
}
