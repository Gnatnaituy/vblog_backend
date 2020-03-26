package com.hasaker.face.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @package com.hasaker.face.vo.request
 * @author 余天堂
 * @create 2020/3/26 16:29
 * @description RequestFriendListVo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "List friends")
public class RequestFriendPageVo {

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
