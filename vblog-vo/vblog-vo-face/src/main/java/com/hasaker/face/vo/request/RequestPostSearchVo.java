package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/3/27 09:32
 * @description RequestPostPageVo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ApiModel(description = "Search post")
public class RequestPostSearchVo extends SearchVo {

    @ApiModelProperty(value = "search keyword")
    private String keyword;

    @ApiModelProperty(value = "filter topic")
    private String topic;
}
