package com.ysmjjsy.goya.component.pojo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PageVO<T extends Serializable> implements VO {

    @Serial
    private static final long serialVersionUID = -1048047352526655922L;

    @Schema(title = "总记录数")
    private long totalCount = 0;

    @Schema(title = "总页数")
    private int totalPages = 0;

    @Schema(title = "每页记录数")
    private int pageSize = 1;

    @Schema(title = "当前页码")
    private int pageIndex = 1;

    @Schema(title = "当前页记录数")
    private int pageCount = 0;

    @Schema(title = "数据")
    private Collection<T> data;

    public static <T extends Serializable> PageVO<T> of(Collection<T> data) {
        PageVO<T> page = new PageVO<>();
        page.data = data;
        return page;
    }

    public static <T extends Serializable> PageVO<T> of(long totalCount, int totalPages, int pageSize, int pageIndex, int pageCount, Collection<T> data) {
        PageVO<T> page = new PageVO<>();
        page.totalCount = totalCount;
        page.totalPages = totalPages;
        page.pageSize = pageSize;
        page.pageIndex = pageIndex;
        page.pageCount = pageCount;
        page.data = data;
        return page;
    }
}
