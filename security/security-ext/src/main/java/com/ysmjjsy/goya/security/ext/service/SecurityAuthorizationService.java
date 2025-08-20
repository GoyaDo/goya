package com.ysmjjsy.goya.security.ext.service;

import com.ysmjjsy.goya.module.rest.service.BaseService;
import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorization;
import com.ysmjjsy.goya.security.ext.repository.SecurityAuthorizationRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:44
 */
@Slf4j
@Service
public class SecurityAuthorizationService extends BaseService<SecurityAuthorization, SecurityAuthorizationRepository> {

    public Optional<SecurityAuthorization> findByState(String state) {
        Optional<SecurityAuthorization> result = this.getRepository().findByState(state);
        log.debug("[Goya] |- SecurityAuthorization Service findByState.");
        return result;
    }

    public Optional<SecurityAuthorization> findByAuthorizationCode(String authorizationCode) {
        Optional<SecurityAuthorization> result = this.getRepository().findByAuthorizationCodeValue(authorizationCode);
        log.debug("[Goya] |- SecurityAuthorization Service findByAuthorizationCode.");
        return result;
    }

    public Optional<SecurityAuthorization> findByAccessToken(String accessToken) {
        Optional<SecurityAuthorization> result = this.getRepository().findByAccessTokenValue(accessToken);
        log.debug("[Goya] |- SecurityAuthorization Service findByAccessToken.");
        return result;
    }

    public Optional<SecurityAuthorization> findByRefreshToken(String refreshToken) {
        Optional<SecurityAuthorization> result = this.getRepository().findByRefreshTokenValue(refreshToken);
        log.debug("[Goya] |- SecurityAuthorization Service findByRefreshToken.");
        return result;
    }

    public Optional<SecurityAuthorization> findByOidcIdTokenValue(String idToken) {
        Optional<SecurityAuthorization> result = this.getRepository().findByOidcIdTokenValue(idToken);
        log.debug("[Goya] |- SecurityAuthorization Service findByOidcIdTokenValue.");
        return result;
    }

    public Optional<SecurityAuthorization> findByUserCodeValue(String userCode) {
        Optional<SecurityAuthorization> result = this.getRepository().findByUserCodeValue(userCode);
        log.debug("[Goya] |- SecurityAuthorization Service findByUserCodeValue.");
        return result;
    }

    public Optional<SecurityAuthorization> findByDeviceCodeValue(String deviceCode) {
        Optional<SecurityAuthorization> result = this.getRepository().findByDeviceCodeValue(deviceCode);
        log.debug("[Goya] |- SecurityAuthorization Service findByDeviceCodeValue.");
        return result;
    }

    public Optional<SecurityAuthorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(String token) {

        Specification<SecurityAuthorization> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("state"), token));
            predicates.add(criteriaBuilder.equal(root.get("authorizationCodeValue"), token));
            predicates.add(criteriaBuilder.equal(root.get("accessTokenValue"), token));
            predicates.add(criteriaBuilder.equal(root.get("refreshTokenValue"), token));
            predicates.add(criteriaBuilder.equal(root.get("oidcIdTokenValue"), token));
            predicates.add(criteriaBuilder.equal(root.get("userCodeValue"), token));
            predicates.add(criteriaBuilder.equal(root.get("deviceCodeValue"), token));

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        Optional<SecurityAuthorization> result = this.getRepository().findOne(specification);
        log.trace("[Goya] |- SecurityAuthorization Service findByDetection.");
        return result;
    }

    public void clearHistoryToken() {
        this.getRepository().deleteByRefreshTokenExpiresAtBefore(LocalDateTime.now());
        log.debug("[Goya] |- SecurityAuthorization Service clearExpireAccessToken.");
    }

    public List<SecurityAuthorization> findAvailableAuthorizations(String registeredClientId, String principalName) {
        List<SecurityAuthorization> authorizations = this.getRepository().findAllByRegisteredClientIdAndPrincipalNameAndAccessTokenExpiresAtAfter(registeredClientId, principalName, LocalDateTime.now());
        log.debug("[Goya] |- SecurityAuthorization Service findAvailableAuthorizations.");
        return authorizations;
    }

    public int findAuthorizationCount(String registeredClientId, String principalName) {
        List<SecurityAuthorization> authorizations = findAvailableAuthorizations(registeredClientId, principalName);
        int count = 0;
        if (CollectionUtils.isNotEmpty(authorizations)) {
            count = authorizations.size();
        }
        log.debug("[Goya] |- SecurityAuthorization Service current authorization count is [{}].", count);
        return count;
    }
}
