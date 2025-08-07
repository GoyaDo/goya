package com.ysmjjsy.goya.module.jpa.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.distributedid.utils.SnowflakeIdUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE {table_name} SET del_flag = true WHERE id = ?")
@SQLRestriction("del_flag = false")
public abstract class BaseJpaEntity extends BaseDbEntity {

    @Serial
    private static final long serialVersionUID = 8227316877620962339L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @Override
    public String getId() {
        return super.id;
    }

    @Override
    @Column(name = "created_time", updatable = false, nullable = false)
    @CreatedDate
    public LocalDateTime getCreatedTime() {
        return super.createdTime;
    }

    @Override
    @Column(name = "updated_time", nullable = false)
    @LastModifiedDate
    public LocalDateTime getUpdatedTime() {
        return super.updatedTime;
    }

    @Override
    @CreatedBy
    @Column(name = "create_by")
    public String getCreateBy() {
        return super.createBy;
    }

    @Override
    @LastModifiedBy
    @Column(name = "update_by")
    public String getUpdateBy() {
        return super.updateBy;
    }

    @Override
    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return super.version;
    }

    @Override
    @Column(name = "del_flag", nullable = false)
    public Boolean getDelFlag() {
        return super.delFlag;
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = SnowflakeIdUtil.nextIdStr();
        }
        this.setCreatedTime(LocalDateTime.now());
        this.setUpdatedTime(LocalDateTime.now());

        // 明确初始化版本号
        if (this.version == null) {
            this.version = 0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedTime(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseJpaEntity that = (BaseJpaEntity) o;
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
