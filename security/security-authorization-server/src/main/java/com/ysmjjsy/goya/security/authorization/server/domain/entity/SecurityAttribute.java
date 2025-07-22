package com.ysmjjsy.goya.security.authorization.server.domain.entity;

import com.ysmjjsy.goya.component.db.domain.BaseJpaAggregate;
import com.ysmjjsy.goya.security.authorization.server.listener.SecurityAttributeEntityListener;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 10:15
 */
@ToString(callSuper = true)
@Getter
@Setter
@Schema(name = "系统安全属性数据")
@Entity
@Table(name = "security_attribute", indexes = {@Index(name = "security_attribute_id_idx", columnList = "attribute_id")})
@EntityListeners(value = {SecurityAttributeEntityListener.class})
public class SecurityAttribute extends BaseJpaAggregate {
    @Serial
    private static final long serialVersionUID = -5272634414594774626L;

    @Schema(name = "默认权限代码")
    @Column(name = "attribute_code", length = 128)
    private String attributeCode;

    @Schema(name = "请求方法")
    @Column(name = "request_method", length = 20)
    private String requestMethod;

    @Schema(name = "服务ID")
    @Column(name = "service_id", length = 128)
    private String serviceId;

    @Schema(name = "接口所在类")
    @Column(name = "class_name", length = 512)
    private String className;

    @Schema(name = "接口对应方法")
    @Column(name = "method_name", length = 128)
    private String methodName;

    @Schema(name = "请求URL")
    @Column(name = "url", length = 2048)
    private String url;

    @Schema(name = "表达式", description = "Security表达式字符串，通过该值设置动态权限")
    @Column(name = "web_expression", length = 128)
    private String webExpression;

    /**
     * 角色描述,UI界面显示使用
     */
    @Schema(name = "备注")
    @Column(name = "description", length = 512)
    private String description;

    @Schema(name = "属性对应权限", title = "根据属性关联权限数据")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "security_attribute_permission",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"attributeId", "permissionId"})},
            indexes = {@Index(name = "security_attribute_permission_aid_idx", columnList = "attributeId"), @Index(name = "security_attribute_permission_pid_idx", columnList = "permissionId")})
    private Set<SecurityPermission> permissions = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SecurityAttribute that = (SecurityAttribute) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
