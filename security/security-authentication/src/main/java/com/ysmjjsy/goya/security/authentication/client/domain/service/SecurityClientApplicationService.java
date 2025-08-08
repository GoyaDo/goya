package com.ysmjjsy.goya.security.authentication.client.domain.service;

import com.ysmjjsy.goya.security.authentication.client.domain.converter.SecurityClientApplicationToRegisteredClientConverter;
import com.ysmjjsy.goya.security.authentication.client.domain.definition.AbstractRegisteredClientService;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientApplication;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.authentication.client.domain.repository.SecurityClientApplicationRepository;
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
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:54
 */
@Slf4j
@Service
public class SecurityClientApplicationService extends AbstractRegisteredClientService<SecurityClientApplication,SecurityClientApplicationRepository> {

    private final SecurityClientApplicationRepository securityClientApplicationRepository;
    private final Converter<SecurityClientApplication, RegisteredClient> objectConverter;

    public SecurityClientApplicationService(ApplicationEventPublisher applicationEventPublisher,
                                            SecurityClientApplicationRepository securityClientApplicationRepository) {
        super(applicationEventPublisher);
        this.securityClientApplicationRepository = securityClientApplicationRepository;
        this.objectConverter = new SecurityClientApplicationToRegisteredClientConverter();
    }

    public SecurityClientApplication saveAndFlush(SecurityClientApplication entity) {
        SecurityClientApplication application = getRepository().saveAndFlush(entity);
        if (ObjectUtils.isNotEmpty(application)) {
            save(Objects.requireNonNull(objectConverter.convert(application)));
            return application;
        } else {
            log.error("[Goya] |- OAuth2ApplicationService saveOrUpdate error!");
            throw new NullPointerException("save or update OAuth2Application failed");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public SecurityClientApplication authorize(String applicationId, String[] scopeIds) {

        Set<SecurityClientScope> scopes = new HashSet<>();
        for (String scopeId : scopeIds) {
            SecurityClientScope scope = new SecurityClientScope();
            scope.setId(scopeId);
            scopes.add(scope);
        }

        SecurityClientApplication oldApplication = findById(applicationId);
        oldApplication.setScopes(scopes);

        return saveAndFlush(oldApplication);
    }

    public SecurityClientApplication findByClientId(String clientId) {
        return securityClientApplicationRepository.findByClientId(clientId);
    }


}
