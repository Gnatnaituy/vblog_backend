package com.hasaker.common.utils;

import com.hasaker.common.consts.MessageConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author 余天堂
 * @since 2019/11/15 09:55
 * @description 国际化资源转换
 */
@Slf4j
public class MessageUtils {

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);

        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     *
     * @Title: message
     * @Description: 根据消息key获取本地描述信息
     * @param: @param code
     * @param: @return
     * @return: String
     * @throws
     */
    public static String message(String code) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        try {
            return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch(Exception e){
            log.error(e.getMessage(),e);

            return "未知错误!";
        }
    }


    /**
     *
     * @Title: success
     * @Description: 调用成功
     * @param: @return
     * @return: String
     * @throws
     */
    public static String success() {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);

        return messageSource.getMessage(MessageConsts.SUCCESS, null, LocaleContextHolder.getLocale());
    }

    /**
     *
     * @Title: failure
     * @Description: 调用失败
     * @param: @return
     * @return: String
     * @throws
     */
    public static String failure() {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);

        return messageSource.getMessage(MessageConsts.ERROR, null, LocaleContextHolder.getLocale());
    }

    /**
     *
     * @Title: exception
     * @Description: 调用异常
     * @param: @return
     * @return: String
     * @throws
     */
    public static String exception() {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);

        return messageSource.getMessage(MessageConsts.EXCEPTION, null, LocaleContextHolder.getLocale());
    }
}
