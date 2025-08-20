package com.ysmjjsy.goya.module.mybatisplus.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 11:08
 */
@Getter
@Setter
public abstract class BaseMpEntity extends BaseDbEntity {

    @Serial
    private static final long serialVersionUID = 6566025330159782110L;

    @TableId
    protected String id;

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedTime;

    @TableField(fill = FieldFill.INSERT)
    protected String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    @Version
    @TableField(fill = FieldFill.INSERT)
    protected Integer version;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Boolean delFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseMpEntity that = (BaseMpEntity) o;
        return Objects.equal(getId(), that.getId()) && Objects.equal(getCreatedTime(), that.getCreatedTime()) && Objects.equal(getUpdatedTime(), that.getUpdatedTime()) && Objects.equal(getCreateBy(), that.getCreateBy()) && Objects.equal(getUpdateBy(), that.getUpdateBy()) && Objects.equal(getVersion(), that.getVersion()) && Objects.equal(getDelFlag(), that.getDelFlag());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getId(), getCreatedTime(), getUpdatedTime(), getCreateBy(), getUpdateBy(), getVersion(), getDelFlag());
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
