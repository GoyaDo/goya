package com.ysmjjsy.goya.component.pojo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * <p>分页对象</p>
 *
 * @author goya
 * @since 2025/6/13 11:31
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class PageVO<T extends Serializable> implements VO {

    @Serial
    private static final long serialVersionUID = -1048047352526655922L;

    @Schema(title = "总记录数")
    private int totalCount = 0;

    @Schema(title = "每页记录数")
    private int pageSize = 1;

    @Schema(title = "当前页码")
    private int pageIndex = 1;

    @Schema(title = "数据")
    private Collection<T> data;

    public static <T extends Serializable> PageVO<T> of(Collection<T> data) {
        PageVO<T> page = new PageVO<>();
        page.data = data;
        return page;
    }

    public static <T extends Serializable> PageVO<T> of(int totalCount, int pageSize, int pageIndex, Collection<T> data) {
        PageVO<T> page = new PageVO<>();
        page.totalCount = totalCount;
        page.pageSize = pageSize;
        page.pageIndex = pageIndex;
        page.data = data;
        return page;
    }
}
