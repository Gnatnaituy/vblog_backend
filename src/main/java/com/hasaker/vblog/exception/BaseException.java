package com.hasaker.vblog.exception;

import com.hasaker.vblog.utils.MessageUtils;

/**
 * @author 余天堂
 * @since 2019/11/15 09:43
 * @description 
 */
public class BaseException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6786064051478878004L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String code, String defaultMessage) {
        this(null, code, null, defaultMessage);
    }

    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args, String message) {
        this(null,code, args, message);
    }

    public BaseException(String code, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("ERROR CODE: ");
        sb.append(code).append(", ERROR MESSAGE: ");
        String message;
        if (defaultMessage != null) {
            message = MessageUtils.message(defaultMessage, args);
            sb.append(message);
        }

        return sb.toString();
    }
}
