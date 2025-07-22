package com.ysmjjsy.goya.security.authentication.client.domain.service;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.security.authentication.client.domain.converter.SecurityClientDeviceToRegisteredClientConverter;
import com.ysmjjsy.goya.security.authentication.client.domain.definition.AbstractRegisteredClientService;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientDevice;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.authentication.client.domain.repository.SecurityClientDeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Description: OAuth2DeviceService </p>
 *
 * @author goya
 * @since 2023/5/15 16:36
 */
@Slf4j
@Service
public class SecurityClientDeviceService extends AbstractRegisteredClientService<SecurityClientDevice> {

    private final SecurityClientDeviceRepository securityClientDeviceRepository;
    private final Converter<SecurityClientDevice, RegisteredClient> oauth2DeviceToRegisteredClientConverter;

    public SecurityClientDeviceService(ApplicationEventPublisher applicationEventPublisher, SecurityClientDeviceRepository securityClientDeviceRepository) {
        super(applicationEventPublisher);
        this.securityClientDeviceRepository = securityClientDeviceRepository;
        this.oauth2DeviceToRegisteredClientConverter = new SecurityClientDeviceToRegisteredClientConverter();
    }

    @Override
    public BaseRepository<SecurityClientDevice, String> getRepository() {
        return securityClientDeviceRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SecurityClientDevice saveAndFlush(SecurityClientDevice entity) {
        SecurityClientDevice device = super.saveAndFlush(entity);
        if (ObjectUtils.isNotEmpty(device)) {
            save(Objects.requireNonNull(oauth2DeviceToRegisteredClientConverter.convert(device)));
            return device;
        } else {
            log.error("[Goya] |- OAuth2DeviceService saveOrUpdate error, rollback data!");
            throw new NullPointerException("save or update OAuth2DeviceService failed");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public SecurityClientDevice authorize(String deviceId, String[] scopeIds) {

        Set<SecurityClientScope> scopes = new HashSet<>();
        for (String scopeId : scopeIds) {
            SecurityClientScope scope = new SecurityClientScope();
            scope.setId(scopeId);
            scopes.add(scope);
        }

        SecurityClientDevice oldDevice = findById(deviceId);
        oldDevice.setScopes(scopes);

        return saveAndFlush(oldDevice);
    }

    public boolean activate(String clientId, boolean isActivated) {
        int result = securityClientDeviceRepository.activate(clientId, isActivated);
        return result != 0;
    }
}
