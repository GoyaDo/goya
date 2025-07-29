package com.ysmjjsy.goya.component.db.domain;

import com.ysmjjsy.goya.component.pojo.domain.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.dromara.hutool.core.data.id.IdUtil;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

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
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE {table_name} SET del_flag = true WHERE id = ?")
@SQLRestriction("del_flag = false")
public abstract class BaseJpaAggregate extends AbstractAggregateRoot<BaseJpaAggregate> implements Entity {

    @Id
    @Column(name = "id")
    protected String id;

    @Column(name = "created_time", nullable = false, updatable = false)
    @Setter(AccessLevel.PROTECTED)
    protected LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    @Setter(AccessLevel.PROTECTED)
    protected LocalDateTime updatedTime;

    @Column(name = "create_by")
    @CreatedBy
    protected String createBy;

    @Column(name = "update_by")
    @LastModifiedBy
    protected String updateBy;

//    @Version
    @Column(name = "version")
    @Setter(AccessLevel.PRIVATE)
    protected Integer version = 0;

    /**
     * 逻辑删除标识：0-正常，1-已删除
     */
    @Column(name = "del_flag", nullable = false)
    protected Boolean delFlag = false;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            //TODO 雪花算法
            this.id = IdUtil.getSnowflakeNextIdStr();
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
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BaseJpaAggregate that = (BaseJpaAggregate) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
