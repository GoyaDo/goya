package com.ysmjjsy.goya.component.domain.command;

import com.ysmjjsy.goya.component.domain.dispatcher.DomainEventDispatcher;
import com.ysmjjsy.goya.component.domain.event.DomainEventQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Function;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 14:24
 */
@Slf4j
public class OneTransactionCommandInvoker implements CommandInvoker{

    private final TransactionTemplate transactionTemplate;
    private final DomainEventDispatcher dispatcher;

    public OneTransactionCommandInvoker(PlatformTransactionManager tx,
                                        DomainEventDispatcher dispatcher) {
        this.transactionTemplate = new TransactionTemplate(tx);
        this.dispatcher = dispatcher;
    }

    @Override
    public <R> R invoke(Function<DomainEventQueue, R> block) {
        return transactionTemplate.execute(status -> {
            DomainEventQueue queue = new DomainEventQueue();
            R result = block.apply(queue);
            dispatcher.dispatchNow(queue);
            return result;
        });
    }
}
