package com.ysmjjsy.goya.component.common.chain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>抽象责任链上下文</p>
 *
 * @author goya
 * @since 2025/7/24 23:33
 */
public class AbstractChainContext<T> implements CommandLineRunner {

    private final Map<String, List<AbstractChainHandler<T>>> abstractChainHandlerContainer = Maps.newHashMap();

    /**
     * 责任链组件执行
     *
     * @param mark         责任链组件标识
     * @param requestParam 请求参数
     */
    public void handler(String mark, T requestParam) {
        List<AbstractChainHandler<T>> abstractChainHandlers = abstractChainHandlerContainer.get(mark);
        if (CollectionUtils.isEmpty(abstractChainHandlers)) {
            throw new GoyaRuntimeException(String.format("[%s] Chain of Responsibility ID is undefined.", mark));
        }
        abstractChainHandlers.forEach(each -> each.handler(requestParam));
    }

    @Override
    @SuppressWarnings("all")
    public void run(String... args) throws Exception {
        Map<String, AbstractChainHandler> chainFilterMap = ApplicationContextHolder
                .getBeansOfType(AbstractChainHandler.class);
        chainFilterMap.forEach((beanName, bean) -> {
            List<AbstractChainHandler<T>> abstractChainHandlers = abstractChainHandlerContainer.get(bean.mark());
            if (CollectionUtils.isEmpty(abstractChainHandlers)) {
                abstractChainHandlers = Lists.newArrayList();
            }
            abstractChainHandlers.add(bean);
            List<AbstractChainHandler<T>> actualAbstractChainHandlers = abstractChainHandlers.stream()
                    .sorted(Comparator.comparing(Ordered::getOrder))
                    .collect(Collectors.toList());
            abstractChainHandlerContainer.put(bean.mark(), actualAbstractChainHandlers);
        });
    }
}
