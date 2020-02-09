package com.hasaker.user.exception.base;

/**
 * @package com.hasaker.vblog.base
 * @author 余天堂
 * @create 2019/12/24 14:56
 * @description VBlogException
 */
public class CommonException extends BaseException {

    private static final long serialVersionUID = -2121391155040696048L;

    public CommonException(BaseResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum.getCode(), args, message);
    }

    public CommonException(BaseResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum.getCode(), args, message, cause);
    }

    public CommonException(BaseResponseEnum responseEnum) {
        super(responseEnum.getCode(), responseEnum.getMessage());
    }
}
