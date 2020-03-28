package com.hasaker.common.vo;

import com.hasaker.common.consts.Consts;
import com.hasaker.common.consts.MessageConsts;
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
@ApiModel(value = "Ajax")
public class Ajax<T> implements Serializable {

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "message")
    private String message;

    @ApiModelProperty(value = "object")
    private T data;

    // 单例模式
    public static Ajax ajax = null;

    public static synchronized Ajax getInstance() {
        if (ajax == null) {
            ajax = new Ajax();
        }

        return ajax;
    }

    /** 成功 */
    public static Ajax success() {
        Ajax ajax = new Ajax();
        ajax.setCode(Consts.SUCCESS);
        ajax.setMessage(MessageConsts.SUCCESS);

        return ajax;
    }

    public static Ajax success(String message) {
        Ajax ajax = new Ajax();
        ajax.setCode(Consts.SUCCESS);
        ajax.setMessage(message);

        return ajax;
    }

    /** 失败 */
    public static Ajax failure() {
        Ajax ajax = new Ajax();
        ajax.setCode(Consts.FAILURE);
        ajax.setMessage(MessageConsts.FAILED);

        return ajax;
    }

    public static Ajax failure(String message) {
        Ajax ajax = new Ajax();
        ajax.setCode(Consts.FAILURE);
        ajax.setMessage(message);

        return ajax;
    }


    /** 单例返回 */
    public Ajax<T> successT(T data) {
        Ajax<T> ajax = new Ajax<>();
        ajax.setCode(Consts.SUCCESS);
        ajax.setMessage(MessageConsts.SUCCESS);
        ajax.setData(data);

        return ajax;
    }

    public Ajax<T> successT(String message, T data) {
        Ajax<T> ajax = new Ajax<>();
        ajax.setCode(Consts.SUCCESS);
        ajax.setMessage(message);
        ajax.setData(data);

        return ajax;
    }
}
