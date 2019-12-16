package com.hasaker.vblog.exception;

import com.hasaker.vblog.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 余天堂
 * @since 12/15/19 16:43
 * @description 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionBody {

    private String code;

    private String message;

    private Object object;

    public ExceptionBody(BaseException exceptionInfo) {
        this.code = exceptionInfo.getCode();
        this.message = exceptionInfo.getMessage();
    }


}
