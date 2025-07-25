package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.db.domain.BaseJpaAggregate;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:47
 */
@ToString(callSuper = true)
@Setter
@Getter
@Schema(name = "物联网产品")
@Entity
@Table(name = "security_client_product", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_key"})},
        indexes = {@Index(name = "security_client_product_pid_idx", columnList = "id"), @Index(name = "oauth2_product_ipk_idx", columnList = "product_key")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_PRODUCT)
public class SecurityClientProduct extends BaseJpaAggregate {

    @Serial
    private static final long serialVersionUID = 213812028998321356L;

    @Column(name = "product_key", length = 32, unique = true)
    private String productKey;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SecurityClientProduct that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getProductKey(), that.getProductKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getProductKey());
    }
}
