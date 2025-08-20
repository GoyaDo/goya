package com.ysmjjsy.goya.module.tenant.core;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.module.jpa.domain.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:14
 */
@SuppressWarnings("all")
@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class BaseJpaTenantEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = 8227316877620962339L;

    @Column(name = "tenant_id")
    protected String tenantId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseJpaTenantEntity that = (BaseJpaTenantEntity) o;
        return Objects.equal(getTenantId(), that.getTenantId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getTenantId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tenantId", tenantId)
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
