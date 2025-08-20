package com.ysmjjsy.goya.component.core.stragegy;

import com.ysmjjsy.goya.component.common.strategy.AbstractExecuteStrategy;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import com.ysmjjsy.goya.component.core.init.ApplicationInitializingEvent;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;
import org.springframework.context.ApplicationListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>策略选择器</p>
 *
 * @author goya
 * @since 2025/7/24 23:40
 */
@SuppressWarnings("all")
public class AbstractStrategyChoose implements ApplicationListener<ApplicationInitializingEvent> {

    /**
     * 执行策略集合
     */
    private final Map<String, AbstractExecuteStrategy> abstractExecuteStrategyMap = new HashMap<>();

    /**
     * 根据 mark 查询具体策略
     *
     * @param mark 策略标识
     * @return 实际执行策略
     */
    public AbstractExecuteStrategy choose(String mark) {
        return Optional.ofNullable(abstractExecuteStrategyMap.get(mark)).orElseThrow(() -> new GoyaRuntimeException(String.format("[%s] 策略未定义", mark)));
    }

    /**
     * 根据 mark 查询具体策略并执行
     *
     * @param mark         策略标识
     * @param requestParam 执行策略入参
     * @param <REQUEST>    执行策略入参范型
     */
    public <REQUEST> void chooseAndExecute(String mark, REQUEST requestParam) {
        AbstractExecuteStrategy executeStrategy = choose(mark);
        executeStrategy.execute(requestParam);
    }

    /**
     * 根据 mark 查询具体策略并执行，带返回结果
     *
     * @param mark         策略标识
     * @param requestParam 执行策略入参
     * @param <REQUEST>    执行策略入参范型
     * @param <RESPONSE>   执行策略出参范型
     * @return
     */
    public <REQUEST, RESPONSE> RESPONSE chooseAndExecuteResp(String mark, REQUEST requestParam) {
        AbstractExecuteStrategy executeStrategy = choose(mark);
        return (RESPONSE) executeStrategy.executeResp(requestParam);
    }

    @Override
    public void onApplicationEvent(ApplicationInitializingEvent event) {
        Map<String, AbstractExecuteStrategy> actual = SpringContextHolder.getBeansOfType(AbstractExecuteStrategy.class);
        actual.forEach((beanName, bean) -> {
            AbstractExecuteStrategy beanExist = abstractExecuteStrategyMap.get(bean.mark());
            if (beanExist != null) {
                throw new GoyaRuntimeException(String.format("[%s] Duplicate execution policy", bean.mark()));
            }
            abstractExecuteStrategyMap.put(bean.mark(), bean);
        });
    }
}
