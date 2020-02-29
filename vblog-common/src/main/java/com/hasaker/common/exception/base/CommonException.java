package com.hasaker.common.exception.base;

import com.hasaker.common.base.IEnum;
import lombok.Getter;

/**
 * @package com.hasaker.vblog.base
 * @author 余天堂
 * @create 2019/12/24 14:56
 * @description VBlogException
 */
@Getter
public class CommonException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    public CommonException(IEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }
}
