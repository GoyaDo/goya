package com.ysmjjsy.goya.security.authorization.properties;

import com.ysmjjsy.goya.component.pojo.enums.Target;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.enums.Certificate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 22:45
 */
@Data
@ConfigurationProperties(prefix = SecurityConstants.PROPERTY_PREFIX_SECURITY_AUTHORIZATION)
public class SecurityAuthorizationProperties {


    /**
     * Token 校验是采用远程方式还是本地方式。
     */
    private Target validate = Target.REMOTE;

    /**
     * 是否使用严格模式。严格模式一定要求有权限，非严格模式没有权限管控的接口，只要认证通过也可以使用。
     */
    private Boolean strict = true;

    /**
     * JWT的密钥或者密钥对(JSON Web Key) 配置
     */
    private Jwk jwk = new Jwk();
    /**
     * 指定 Request Matcher 静态安全规则
     */
    private Matcher matcher = new Matcher();

    @Data
    public static class Jwk {

        /**
         * 证书策略：standard OAuth2 标准证书模式；custom 自定义证书模式
         */
        private Certificate certificate = Certificate.CUSTOM;
        /**
         * jks证书文件路径
         */
        private String jksKeyStore = "classpath*:certificate/goya.jks";
        /**
         * jks证书密码
         */
        private String jksKeyPassword = "ysmjjsy";
        /**
         * jks证书密钥库密码
         */
        private String jksStorePassword = "ysmjjsy";
        /**
         * jks证书别名
         */
        private String jksKeyAlias = "ysmjjsy";
    }

    /**
     * 用于手动的指定 Request Matcher 安全规则。
     * <p>
     * permitAll 比较常用，因此先只增加该项。后续可根据需要添加
     */
    @Data
    public static class Matcher {
        /**
         * 静态资源过滤
         */
        private List<String> staticResources;
        /**
         * Security "permitAll" 权限列表。
         */
        private List<String> permitAll;
        /**
         * 只校验是否请求中包含Token，不校验Token中是否包含该权限的资源
         */
        private List<String> hasAuthenticated;

    }
}
