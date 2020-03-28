package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @package com.hasaker.common.vo
 * @author 余天堂
 * @create 2020/3/28 01:52
 * @description PageVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "Base search vo")
public class SearchVo {

    @ApiModelProperty(value = "Current user ID", hidden = true)
    private Long userId;

    @NonNull
    @ApiModelProperty(value = "page start")
    private Integer start;

    @NonNull
    @ApiModelProperty(value = "page size")
    private Integer size;
}
