package com.hasaker.post.exception.enums;

import com.hasaker.common.exception.base.BaseExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @package com.hasaker.post.exception.enums
 * @author 余天堂
 * @create 2020/3/22 19:48
 * @description PostExceptionEnum
 */
@Getter
@AllArgsConstructor
public enum PostExceptionEnum implements BaseExceptionAssert {

    POST_NOT_EXISTS("POST_EXCEPTION_001", "Post not exists"),
    POST_IMAGE_NOT_EXISTS("POST_EXCEPTION_002", "Post image not exists"),
    POST_TOPIC_NOT_EXISTS("POST_EXCEPTION_003", "Post topic not exists"),
    COMMENT_NOT_EXISTS("POST_EXCEPTION_004", "Comment not exists"),
    TOPIC_NOT_EXISTS("POST_EXCEPTION_005", "Topic not exists");

    private String code;
    private String message;
}
