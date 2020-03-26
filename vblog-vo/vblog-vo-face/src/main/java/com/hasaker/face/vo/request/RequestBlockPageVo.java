package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/3/26 16:50
 * @description RequestBlockPageVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "List blocked users")
public class RequestBlockPageVo {

    @NonNull
    @ApiModelProperty(value = "userId", hidden = true)
    private String userId;

    @NonNull
    @ApiModelProperty(value = "page start")
    private Integer start;

    @NonNull
    @ApiModelProperty(value = "page size")
    private Integer size;
}
