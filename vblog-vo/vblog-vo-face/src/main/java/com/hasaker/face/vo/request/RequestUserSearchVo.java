package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/3/26 15:06
 * @description RequestUserSearchVo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ApiModel(description = "Search user")
public class RequestUserSearchVo extends SearchVo {

    @ApiModelProperty(value = "search keyword")
    private String keyword;
}
