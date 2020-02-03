package com.hasaker.common.consts;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 余天堂
 * @since 2019/10/31 17:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AjaxResult")
public class AjaxResult<T> implements Serializable {

    private static final long serialVersionUID = -6023771566281922918L;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "message")
    private String message;

    @ApiModelProperty(value = "object")
    private T object;

    // 单例模式
    public static AjaxResult ajaxResult = null;

    public static synchronized AjaxResult getInstance() {
        if (ajaxResult == null) {
            ajaxResult = new AjaxResult();
        }

        return ajaxResult;
    }

    /** 成功 */
    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.SUCCESS);

        return ajaxResult;
    }

    public static AjaxResult success(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.SUCCESS);
        ajaxResult.setMessage(message);

        return ajaxResult;
    }

    public static AjaxResult success(Object object) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.SUCCESS);
        ajaxResult.setObject(object);

        return ajaxResult;
    }

    public static AjaxResult success(String message, Object object) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.SUCCESS);
        ajaxResult.setMessage(message);
        ajaxResult.setObject(object);

        return ajaxResult;
    }

    /** 失败 */
    public static AjaxResult failure() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.ERROR);

        return ajaxResult;
    }

    public static AjaxResult failure(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.ERROR);
        ajaxResult.setMessage(message);

        return ajaxResult;
    }

    public static AjaxResult failure(Object object) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.ERROR);
        ajaxResult.setObject(object);

        return ajaxResult;
    }

    public static AjaxResult failure(String message, Object object) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(Consts.ERROR);
        ajaxResult.setMessage(message);
        ajaxResult.setObject(object);

        return ajaxResult;
    }
    
    /** 单例返回 */
    public AjaxResult<T> successT(T object) {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setCode(Consts.SUCCESS);
        ajaxResult.setObject(object);
        
        return ajaxResult;
    }

    public AjaxResult<T> successT(String message, T object) {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setCode(Consts.SUCCESS);
        ajaxResult.setMessage(message);
        ajaxResult.setObject(object);

        return ajaxResult;
    }
}
