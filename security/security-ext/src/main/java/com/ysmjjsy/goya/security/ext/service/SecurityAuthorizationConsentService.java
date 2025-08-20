package com.ysmjjsy.goya.security.ext.service;

import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorizationConsent;
import com.ysmjjsy.goya.security.ext.repository.SecurityAuthorizationConsentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:46
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityAuthorizationConsentService {

    private final SecurityAuthorizationConsentRepository securityAuthorizationConsentRepository;

    public Optional<SecurityAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        Optional<SecurityAuthorizationConsent> result = this.securityAuthorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        log.trace("[Goya] |- SecurityAuthorizationConsent Service findByRegisteredClientIdAndPrincipalName.");
        return result;
    }

    public void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        this.securityAuthorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        log.trace("[Goya] |- SecurityAuthorizationConsent Service deleteByRegisteredClientIdAndPrincipalName.");
    }

    public SecurityAuthorizationConsent save(SecurityAuthorizationConsent securityAuthorizationConsent) {
        SecurityAuthorizationConsent result = this.securityAuthorizationConsentRepository.save(securityAuthorizationConsent);
        log.trace("[Goya] |- SecurityAuthorizationConsent Service save.");
        return result;
    }
}
