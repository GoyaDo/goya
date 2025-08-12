package com.ysmjjsy.goya.component.common.pojo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 13:50
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class PageQuery implements Query {

    @Serial
    private static final long serialVersionUID = 173118023083553591L;

    /**
     * 升序
     */
    public static final String ASC = "ASC";

    /**
     * 降序
     */
    public static final String DESC = "DESC";

    /**
     * 默认每页记录数
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数至少为1条")
    @Max(value = 1000, message = "每页条数不能超过1000")
    @Schema(name = "每页数据条数", type = "integer", minimum = "0", maximum = "1000", defaultValue = "10")
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 每页记录数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    @Schema(name = "页码", type = "integer", minimum = "1", defaultValue = "1")
    private int pageIndex = 1;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方向
     */
    private String orderDirection = DESC;

    /**
     * 是否需要总记录数
     */
    private boolean needTotalCount = true;

    public int getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public PageQuery setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public PageQuery setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public int getOffset() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public PageQuery setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageQuery setOrderDirection(String orderDirection) {
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection;
        }
        return this;
    }
}
