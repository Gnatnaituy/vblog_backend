package com.hasaker.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.hasaker.common.vo
 * @author 余天堂
 * @create 2020/3/26 15:45
 * @description PageInfo
 */
@Data
@NoArgsConstructor
@ApiModel(description = "page result")
public class PageInfo<T> {

    /**
     * Generate PageInfo by @org.springframework.data.domain.Page
     * @param page
     */
    public PageInfo(Page<?> page) {
        this.content = new ArrayList<>();
        this.current = page.getNumber();
        this.size = page.getSize();
        this.total = page.getTotalElements();
        this.totalPage = page.getTotalPages();
    }

    @ApiModelProperty(value = "page content")
    private List<T> content;

    @ApiModelProperty(value = "current page")
    private Integer current;

    @ApiModelProperty(value = "page size")
    private Integer size;

    @ApiModelProperty(value = "total amount")
    private Long total;

    @ApiModelProperty(value = "total page")
    private Integer totalPage;
}
