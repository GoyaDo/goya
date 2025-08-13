package com.ysmjjsy.goya.component.db.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.common.pojo.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 10:12
 */
@Getter
@Setter
public abstract class BaseDbEntity implements Entity {

    @Serial
    private static final long serialVersionUID = -3144768033700724819L;

    /**
     * id
     */
    protected String id;

    /**
     * 创建时间
     */
    protected LocalDateTime createdTime;

    /**
     * 更新时间
     */
    protected LocalDateTime updatedTime;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 更新人
     */
    protected String updateBy;

    /**
     * 版本号
     */
    protected Integer version = 0;

    /**
     * 删除标识
     */
    protected Boolean delFlag = false;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseDbEntity that)) return false;
        return Objects.equal(getId(), that.getId()) && Objects.equal(getCreatedTime(), that.getCreatedTime()) && Objects.equal(getUpdatedTime(), that.getUpdatedTime()) && Objects.equal(getCreateBy(), that.getCreateBy()) && Objects.equal(getUpdateBy(), that.getUpdateBy()) && Objects.equal(getVersion(), that.getVersion()) && Objects.equal(getDelFlag(), that.getDelFlag());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getCreatedTime(), getUpdatedTime(), getCreateBy(), getUpdateBy(), getVersion(), getDelFlag());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("createdTime", createdTime)
                .add("updatedTime", updatedTime)
                .add("createBy", createBy)
                .add("updateBy", updateBy)
                .add("version", version)
                .add("delFlag", delFlag)
                .toString();
    }
}
