package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
import com.ysmjjsy.goya.security.authentication.client.domain.definition.AbstractOAuth2RegisteredClient;
import com.ysmjjsy.goya.security.authentication.client.enums.ApplicationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:23
 */
@ToString(callSuper = true)
@Setter
@Schema(name = "OAuth2应用实体")
@Entity
@Table(name = "security_client_application", indexes = {
        @Index(name = "security_client_application_id_idx", columnList = "application_id"),
        @Index(name = "security_client_application_cid_idx", columnList = "client_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_APPLICATION)
public class SecurityClientApplication extends AbstractOAuth2RegisteredClient {

    @Serial
    private static final long serialVersionUID = -7405419421492788177L;

    @Getter
    @Schema(name = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "应用名称不能为空")
    @Column(name = "application_name", length = 128)
    private String applicationName;

    @Getter
    @Schema(name = "应用简称", title = "应用的简称、别名、缩写等信息")
    @Column(name = "abbreviation", length = 64)
    private String abbreviation;

    @Getter
    @Schema(name = "Logo", title = "Logo存储信息，可以是URL或者路径等")
    @Column(name = "logo", length = 1024)
    private String logo;

    @Getter
    @Schema(name = "主页信息", title = "应用相关的主页信息方便查询")
    @Column(name = "homepage", length = 1024)
    private String homepage;

    @Getter
    @Schema(name = "应用类型", title = "用于区分不同类型的应用")
    @Column(name = "application_type")
    @Enumerated(EnumType.ORDINAL)
    private ApplicationType applicationType = ApplicationType.WEB;

    @Schema(name = "应用对应Scope", title = "传递应用对应Scope ID数组")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_APPLICATION_SCOPE)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "security_client_application_scope",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"application_id", "scope_id"})},
            indexes = {@Index(name = "oauth2_application_scope_aid_idx", columnList = "application_id"), @Index(name = "oauth2_application_scope_sid_idx", columnList = "scope_id")})
    private Set<SecurityClientScope> scopes = new HashSet<>();

    @Override
    public Set<SecurityClientScope> getScopes() {
        return scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SecurityClientApplication that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getApplicationName(), that.getApplicationName()) && Objects.equal(getAbbreviation(), that.getAbbreviation()) && Objects.equal(getLogo(), that.getLogo()) && Objects.equal(getHomepage(), that.getHomepage()) && getApplicationType() == that.getApplicationType() && Objects.equal(getScopes(), that.getScopes());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getApplicationName(), getAbbreviation(), getLogo(), getHomepage(), getApplicationType(), getScopes());
    }
}
