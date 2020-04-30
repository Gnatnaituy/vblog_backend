package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/4/30 01:06
 * @description RequestAggregationVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "ES aggregation vo")
public class RequestAggregationVo {

    @ApiModelProperty(value = "Aggregation only for the user if userId exists")
    private Long userId;

    @ApiModelProperty(value = "Aggregation field")
    private String field;

    @ApiModelProperty(value = "Aggregation result size")
    private Integer size;
}
