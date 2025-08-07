package com.ysmjjsy.goya.security.authorization.server.domain.service;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import com.ysmjjsy.goya.security.authorization.server.converter.RequestMappingToSecurityInterfaceConverter;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityInterface;
import com.ysmjjsy.goya.security.authorization.server.domain.repository.SecurityInterfaceRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 09:55
 */
@Slf4j
@Service
public class SecurityInterfaceService extends BaseService<SecurityInterface,String> {

    private final SecurityInterfaceRepository securityInterfaceRepository;
    private final Converter<RequestMapping, SecurityInterface> toSysInterface;

    public SecurityInterfaceService(SecurityInterfaceRepository securityInterfaceRepository) {
        this.securityInterfaceRepository = securityInterfaceRepository;
        this.toSysInterface = new RequestMappingToSecurityInterfaceConverter();
    }

    @Override
    public GoyaRepository<SecurityInterface, String> getRepository() {
        return securityInterfaceRepository;
    }

    public List<SecurityInterface> findAllocatable() {
        // exist sql 结构示例： SELECT * FROM article WHERE EXISTS (SELECT * FROM user WHERE article.uid = user.uid)
        Specification<SecurityInterface> specification = (root, criteriaQuery, criteriaBuilder) -> {

            // 构造Not Exist子查询
            Subquery<SecurityAttribute> subQuery = criteriaQuery.subquery(SecurityAttribute.class);
            Root<SecurityAttribute> subRoot = subQuery.from(SecurityAttribute.class);

            // 构造Not Exist 子查询的where条件
            Predicate subPredicate = criteriaBuilder.equal(subRoot.get("id"), root.get("id"));
            subQuery.where(subPredicate);

            // 构造完整的子查询语句
            //这句话不加会报错，因为他不知道你子查询要查出什么字段。就是上面示例中的子查询中的“select *”的作用
            subQuery.select(subRoot.get("id"));

            // 构造完整SQL
            // 正确的结构参考：SELECT * FROM sys_authority sa WHERE NOT EXISTS ( SELECT * FROM sys_metadata sm WHERE sm.metadata_id = sa.authority_id )
            criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
            return criteriaQuery.getRestriction();
        };

        return this.findAll(specification);
    }

    public List<SecurityInterface> storeRequestMappings(List<RequestMapping> requestMappings) {
        List<SecurityInterface> sysAuthorities = toSysInterfaces(requestMappings);
        return saveAllAndFlush(sysAuthorities);
    }

    private List<SecurityInterface> toSysInterfaces(Collection<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            return requestMappings.stream().map(toSysInterface::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
