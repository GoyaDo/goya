package com.ysmjjsy.goya.security.authorization.server.processor;

import com.google.common.collect.ImmutableList;
import com.ysmjjsy.goya.component.bus.event.strategy.StrategyEventManager;
import com.ysmjjsy.goya.component.exception.transaction.TransactionalRollbackException;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import com.ysmjjsy.goya.security.authorization.domain.AttributeTransmitter;
import com.ysmjjsy.goya.security.authorization.processor.SecurityAttributeAnalyzer;
import com.ysmjjsy.goya.security.authorization.server.converter.SecurityAttributeToAttributeTransmitterConverter;
import com.ysmjjsy.goya.security.authorization.server.converter.SecurityInterfacesToSysAttributesConverter;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityInterface;
import com.ysmjjsy.goya.security.authorization.server.domain.service.SecurityAttributeService;
import com.ysmjjsy.goya.security.authorization.server.domain.service.SecurityInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 09:12
 */
@Slf4j
public class SecurityMetadataDistributeProcessor implements StrategyEventManager<List<AttributeTransmitter>> {


    private final Converter<List<SecurityInterface>, List<SecurityAttribute>> toSysAttributes;
    private final Converter<SecurityAttribute, AttributeTransmitter> toSecurityAttribute;

    private final SecurityAttributeService sysAttributeService;
    private final SecurityInterfaceService sysInterfaceService;
    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    public SecurityMetadataDistributeProcessor(SecurityAttributeService sysAttributeService, SecurityInterfaceService sysInterfaceService, SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
        this.toSysAttributes = new SecurityInterfacesToSysAttributesConverter();
        this.toSecurityAttribute = new SecurityAttributeToAttributeTransmitterConverter();
    }

    @Override
    public void postLocalProcess(List<AttributeTransmitter> data) {
        securityAttributeAnalyzer.processSecurityAttribute(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        log.info("request mapping remote process not support!");
    }

    /**
     * 将SysAuthority表中存在，但是SysSecurityAttribute中不存在的数据同步至SysSecurityAttribute，保证两侧数据一致
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public void postRequestMappings(List<RequestMapping> requestMappings) {
        List<SecurityInterface> storedInterfaces = sysInterfaceService.storeRequestMappings(requestMappings);
        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[Goya] |- [5] Request mapping store success, start to merge security metadata!");

            List<SecurityInterface> sysInterfaces = sysInterfaceService.findAllocatable();

            if (CollectionUtils.isNotEmpty(sysInterfaces)) {
                List<SecurityAttribute> elements = toSysAttributes.convert(sysInterfaces);
                List<SecurityAttribute> result = sysAttributeService.saveAllAndFlush(elements);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[Goya] |- Merge security attribute SUCCESS and FINISHED!");
                } else {
                    log.error("[Goya] |- Merge Security attribute failed!, Please Check!");
                }
            } else {
                log.debug("[Goya] |- No security attribute requires merge, SKIP!");
            }

            distributeServiceSecurityAttributes(storedInterfaces);
        }
    }

    private void distributeServiceSecurityAttributes(List<SecurityInterface> storedInterfaces) {
        storedInterfaces.stream().findAny().ifPresent(item -> {
            String serviceId = item.getServiceId();
            List<SecurityAttribute> sysAttributes = sysAttributeService.findAllByServiceId(item.getServiceId());
            if (CollectionUtils.isNotEmpty(sysAttributes)) {
                List<AttributeTransmitter> securityAttributes = sysAttributes.stream().map(toSecurityAttribute::convert).toList();
                log.debug("[Goya] |- [6] Synchronization permissions to service [{}]", serviceId);
                this.postProcess(serviceId, securityAttributes);
            }
        });
    }

    public void distributeChangedSecurityAttribute(SecurityAttribute sysAttribute) {
        AttributeTransmitter securityAttribute = toSecurityAttribute.convert(sysAttribute);
        postProcess(securityAttribute.getServiceId(), ImmutableList.of(securityAttribute));
    }
}
