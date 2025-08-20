package com.ysmjjsy.goya.security.ext.oauth2;

import com.ysmjjsy.goya.security.ext.converter.OAuth2ToSecurityRegisteredClientConverter;
import com.ysmjjsy.goya.security.ext.converter.SecurityToOAuth2RegisteredClientConverter;
import com.ysmjjsy.goya.security.ext.entity.SecurityRegisteredClient;
import com.ysmjjsy.goya.security.ext.processor.OAuth2JacksonProcessor;
import com.ysmjjsy.goya.security.ext.service.SecurityRegisteredClientService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * <p>基于Jpa 的 RegisteredClient服务</p>
 *
 * @author goya
 * @since 2025/8/19 16:52
 */
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final SecurityRegisteredClientService securityRegisteredClientService;
    private final Converter<SecurityRegisteredClient, RegisteredClient> securityToOAuth2Converter;
    private final Converter<RegisteredClient, SecurityRegisteredClient> oauth2ToSecurityConverter;

    public JpaRegisteredClientRepository(SecurityRegisteredClientService securityRegisteredClientService, PasswordEncoder passwordEncoder) {
        this.securityRegisteredClientService = securityRegisteredClientService;
        OAuth2JacksonProcessor jacksonProcessor = new OAuth2JacksonProcessor();
        this.securityToOAuth2Converter = new SecurityToOAuth2RegisteredClientConverter(jacksonProcessor);
        this.oauth2ToSecurityConverter = new OAuth2ToSecurityRegisteredClientConverter(jacksonProcessor, passwordEncoder);
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        this.securityRegisteredClientService.saveOrUpdate(toEntity(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        SecurityRegisteredClient securityRegisteredClient = this.securityRegisteredClientService.findById(id);
        if (ObjectUtils.isNotEmpty(securityRegisteredClient)) {
            return toObject(securityRegisteredClient);
        }
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.securityRegisteredClientService.findByClientId(clientId).map(this::toObject).orElse(null);
    }

    private RegisteredClient toObject(SecurityRegisteredClient goyaRegisteredClient) {
        return securityToOAuth2Converter.convert(goyaRegisteredClient);
    }

    private SecurityRegisteredClient toEntity(RegisteredClient registeredClient) {
        return oauth2ToSecurityConverter.convert(registeredClient);
    }
}
