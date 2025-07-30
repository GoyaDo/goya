package com.ysmjjsy.goya.component.domain.configuration;

import com.ysmjjsy.goya.component.bus.api.GoyaBus;
import com.ysmjjsy.goya.component.domain.command.CommandInvoker;
import com.ysmjjsy.goya.component.domain.command.OneTransactionCommandInvoker;
import com.ysmjjsy.goya.component.domain.dispatcher.DefaultDomainEventDispatcher;
import com.ysmjjsy.goya.component.domain.dispatcher.DomainEventDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:30
 */
@Slf4j
@AutoConfiguration
public class DomainAutoConfiguration {

    @PostConstruct
    public void init() {
        log.debug("[Goya] |- component [domain] DomainAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandInvoker commandInvoker(PlatformTransactionManager tx,
                                         DomainEventDispatcher dispatcher) {
        CommandInvoker commandInvoker = new OneTransactionCommandInvoker(tx, dispatcher);
        log.trace("[Goya] |- component [domain] |- bean [commandInvoker] register.");
        return commandInvoker;
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainEventDispatcher domainEventDispatcher(GoyaBus goyaBus) {
        DefaultDomainEventDispatcher dispatcher = new DefaultDomainEventDispatcher(goyaBus);
        log.trace("[Goya] |- component [domain] |- bean [domainEventDispatcher] register.");
        return dispatcher;
    }

}
