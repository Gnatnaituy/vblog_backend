package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/3/26 15:06
 * @description RequestUserSearchVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Search user")
public class RequestUserSearchVo {

    @NonNull
    @ApiModelProperty(value = "search keyword")
    private String keyword;

    @NonNull
    @ApiModelProperty(value = "page start")
    private Integer start;

    @NonNull
    @ApiModelProperty(value = "page size")
    private Integer size;
}
