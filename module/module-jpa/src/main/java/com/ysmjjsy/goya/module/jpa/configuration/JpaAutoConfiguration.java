package com.ysmjjsy.goya.module.jpa.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysmjjsy.goya.module.jpa.adapter.JpaRepositoryAdapter;
import com.ysmjjsy.goya.module.jpa.auditing.SecurityAuditorAware;
import com.ysmjjsy.goya.module.jpa.configuration.properties.GoyaJpaProperties;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/22 23:39
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(
        basePackages = "${goya.db.jpa.repository.base-packages}",
        repositoryBaseClass = JpaRepositoryAdapter.class
)
@EntityScan("${goya.db.jpa.entity.base-packages}")
@EnableConfigurationProperties(GoyaJpaProperties.class)
public class JpaAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [jpa] JpaAutoConfiguration auto configure.");
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        SecurityAuditorAware securityAuditorAware = new SecurityAuditorAware();
        log.trace("[Goya] |- module [jpa] |- bean [auditorAware] register.");
        return securityAuditorAware;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        log.trace("[Goya] |- module [jpa] |- bean [jpaQueryFactory] register.");
        return queryFactory;
    }


}
