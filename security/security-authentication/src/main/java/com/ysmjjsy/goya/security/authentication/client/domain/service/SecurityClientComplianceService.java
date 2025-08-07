package com.ysmjjsy.goya.security.authentication.client.domain.service;

import com.google.common.net.HttpHeaders;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientCompliance;
import com.ysmjjsy.goya.security.authentication.client.domain.repository.SecurityClientComplianceRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.http.server.servlet.ServletUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.http.useragent.UserAgentUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: ActionAuditService </p>
 *
 * @author goya
 * @since 2022/7/7 20:37
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityClientComplianceService extends BaseService<SecurityClientCompliance, String> {

    private final SecurityClientComplianceRepository securityClientComplianceRepository;

    @Override
    public GoyaRepository<SecurityClientCompliance, String> getRepository() {
        return securityClientComplianceRepository;
    }

    public Page<SecurityClientCompliance> findByCondition(int pageNumber, int pageSize, String principalName, String clientId, String ip) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SecurityClientCompliance> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(principalName)) {
                predicates.add(criteriaBuilder.equal(root.get("principalName"), principalName));
            }

            if (StringUtils.isNotBlank(clientId)) {
                predicates.add(criteriaBuilder.equal(root.get("clientId"), clientId));
            }

            if (StringUtils.isNotBlank(ip)) {
                predicates.add(criteriaBuilder.equal(root.get("ip"), ip));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public SecurityClientCompliance save(String principalName, String clientId, String operation, HttpServletRequest request) {
        SecurityClientCompliance compliance = toEntity(principalName, clientId, operation, request);
        log.debug("[Goya] |- Sign in user is [{}]", compliance);
        return super.save(compliance);
    }

    private UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgentUtil.parse(request.getHeader(HttpHeaders.USER_AGENT));
    }

    private String getIp(HttpServletRequest request) {
        return ServletUtil.getClientIP(request, "");
    }

    public SecurityClientCompliance toEntity(String principalName, String clientId, String operation, HttpServletRequest request) {
        SecurityClientCompliance audit = new SecurityClientCompliance();
        audit.setPrincipalName(principalName);
        audit.setClientId(clientId);
        audit.setOperation(operation);

        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtils.isNotEmpty(userAgent)) {
            audit.setIp(getIp(request));
            audit.setMobile(userAgent.isMobile());
            audit.setOsName(userAgent.getOs().getName());
            audit.setBrowserName(userAgent.getBrowser().getName());
            audit.setMobileBrowser(userAgent.getBrowser().isMobile());
            audit.setEngineName(userAgent.getEngine().getName());
            audit.setMobilePlatform(userAgent.getPlatform().isMobile());
            audit.setIphoneOrIpod(userAgent.getPlatform().isIPhoneOrIPod());
            audit.setIpad(userAgent.getPlatform().isIPad());
            audit.setIos(userAgent.getPlatform().isIos());
            audit.setAndroid(userAgent.getPlatform().isAndroid());
        }

        return audit;
    }
}
