package com.hasaker.common.handler;

import com.hasaker.common.consts.Ajax;
import com.hasaker.common.consts.MessageConsts;
import com.hasaker.common.exception.base.CommonException;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @package com.hasaker.common.handler
 * @author 余天堂
 * @create 2020/2/28 21:57
 * @description GlobalExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    /**
     * 捕获请求方法异常
     * @param e
     * @return
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public Ajax handleException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException:url:{}", request.getRequestURI());

        return Ajax.failure(MessageConsts.NOT_SUPPORTED_REQUEST_METHOD);
    }

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public Ajax commonExceptionHandler(CommonException e) {
        Ajax ajax = new Ajax();
        ajax.setCode(e.getCode());
        ajax.setMessage(e.getMessage());

        return ajax;
    }

    /**
     * 捕获运行时异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Ajax notFount(RuntimeException e) {
        log.error("RuntimeException:url:{}", request.getRequestURI());
        log.error("RuntimeException:{}", e.getMessage());

        return Ajax.failure(e.getMessage());
    }

    /**
     * 捕获程序异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Ajax exceptionHandler(Exception e) {
        Ajax ajax = new Ajax();
        ajax.setCode(CommonExceptionEnums.INTERNAL_SERVER_ERROR.getCode());
        ajax.setMessage(e.getMessage());

        return ajax;
    }
}
