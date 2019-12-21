package com.hasaker.vblog.exception;

import com.hasaker.vblog.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @package com.hasaker.vblog.exception
 * @author 余天堂
 * @create 2019/12/22 00:59
 * @description BaseException
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private Integer code;

    private String message;

    public BaseException(Status status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
