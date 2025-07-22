package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.db.domain.BaseJpaAggregate;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
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
 * @since 2025/7/21 23:44
 */
@ToString(callSuper = true)
@Setter
@Getter
@Entity
@Table(name = "security_client_compliance", indexes = {
        @Index(name = "security_client_compliance_id_idx", columnList = "id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_COMPLIANCE)
public class SecurityClientCompliance extends BaseJpaAggregate {

    @Serial
    private static final long serialVersionUID = -263580015488006610L;

    @Column(name = "principal_name", length = 128)
    private String principalName;

    @Column(name = "client_id", length = 100)
    private String clientId;

    @Column(name = "ip_address", length = 20)
    private String ip;

    @Column(name = "is_mobile")
    private Boolean mobile = false;

    @Column(name = "os_name", length = 200)
    private String osName;

    @Column(name = "browser_name", length = 50)
    private String browserName;
    @Column(name = "is_mobile_browser")
    private Boolean mobileBrowser = false;

    @Column(name = "engine_name", length = 50)
    private String engineName;

    @Column(name = "is_mobile_platform")
    private Boolean mobilePlatform = false;

    @Column(name = "is_iphone_or_ipod")
    private Boolean iphoneOrIpod = false;

    @Column(name = "is_ipad")
    private Boolean ipad = false;

    @Column(name = "is_ios")
    private Boolean ios = false;

    @Column(name = "is_android")
    private Boolean android = false;

    @Column(name = "operation")
    private String operation;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SecurityClientCompliance that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getPrincipalName(), that.getPrincipalName()) && Objects.equal(getClientId(), that.getClientId()) && Objects.equal(getIp(), that.getIp()) && Objects.equal(getMobile(), that.getMobile()) && Objects.equal(getOsName(), that.getOsName()) && Objects.equal(getBrowserName(), that.getBrowserName()) && Objects.equal(getMobileBrowser(), that.getMobileBrowser()) && Objects.equal(getEngineName(), that.getEngineName()) && Objects.equal(getMobilePlatform(), that.getMobilePlatform()) && Objects.equal(getIphoneOrIpod(), that.getIphoneOrIpod()) && Objects.equal(getIpad(), that.getIpad()) && Objects.equal(getIos(), that.getIos()) && Objects.equal(getAndroid(), that.getAndroid()) && Objects.equal(getOperation(), that.getOperation());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getPrincipalName(), getClientId(), getIp(), getMobile(), getOsName(), getBrowserName(), getMobileBrowser(), getEngineName(), getMobilePlatform(), getIphoneOrIpod(), getIpad(), getIos(), getAndroid(), getOperation());
    }
}
