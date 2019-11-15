package com.hasaker.vblog.base;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hasaker.vblog.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 余天堂
 * @since 2019/11/15 09:43
 * @description 
 */
@Setter
@Getter
public class BaseException extends RuntimeException {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 6786064051358878004L;

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
    private String[] args;

    /**
     * 错误消息
     */
    private String message;

    public BaseException(String module, String code, String[] args, String message) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.message = message;
    }

    public BaseException(String code, String message) {
        this(null, code, null, message);
    }

    public BaseException(String code, String[] args, String message) {
        this(null, code, args, message);
    }

    public BaseException(String code, String[] args, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("ERROR CODE:");
        sb.append(code).append(", ERROR MESSAGE:");
        String message = null;
        if (StringUtils.isNotEmpty(message)) {
            message = MessageUtils.message(message, args);
            sb.append(message);
        }

        return sb.toString();
    }
}
