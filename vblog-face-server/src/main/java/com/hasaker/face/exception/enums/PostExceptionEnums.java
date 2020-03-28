package com.hasaker.face.exception.enums;

import com.hasaker.common.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.face.exception.enums
 * @author 余天堂
 * @create 2020/3/26 21:13
 * @description PostExceptionEnums
 */
@Getter
@AllArgsConstructor
public enum PostExceptionEnums implements BaseExceptionAssert {

    POST_NOT_EXISTS("POST_EXCEPTION_001", "Post not exists"),
    COMMENT_NOT_EXISTS("POST_EXCEPTION_002", "Comment not exists");

    private String code;
    private String message;
}
