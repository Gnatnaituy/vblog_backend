package com.hasaker.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @package com.hasaker.common.vo
 * @author 余天堂
 * @create 2020/3/26 15:45
 * @description PageInfo
 */
@Data
@NoArgsConstructor
public class PageInfo<T> {

    public PageInfo(Page page) {
        this.current = page.getNumber();
        this.size = page.getSize();
        this.total = page.getTotalElements();
        this.totalPage = page.getTotalPages();
    }

    private List<T> content;

    private Integer current;

    private Integer size;

    private Long total;

    private Integer totalPage;
}
