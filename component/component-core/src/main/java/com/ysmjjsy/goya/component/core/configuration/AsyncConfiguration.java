package com.ysmjjsy.goya.component.core.configuration;

import com.ysmjjsy.goya.component.common.utils.ThreadUtils;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import com.ysmjjsy.goya.component.core.utils.EnvironmentUtils;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/12 17:51
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class AsyncConfiguration implements AsyncConfigurer {

    /**
     * 核心线程数 = cpu 核心数 + 1
     */
    private final int core = Runtime.getRuntime().availableProcessors() + 1;

    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [core] AsyncConfiguration auto configure.");
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        // daemon 必须为 true
        BasicThreadFactory.Builder builder = new BasicThreadFactory.Builder().daemon(true);
        if (EnvironmentUtils.isVirtual()) {
            builder.namingPattern("virtual-schedule-pool-%d").wrappedFactory(new VirtualThreadTaskExecutor().getVirtualThreadFactory());
        } else {
            builder.namingPattern("schedule-pool-%d");
        }
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(core,
                builder.build(),
                new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                ThreadUtils.printException(r, t);
            }
        };
        this.scheduledExecutorService = scheduledThreadPoolExecutor;
        return scheduledThreadPoolExecutor;
    }

    /**
     * 销毁事件
     */
    @PreDestroy
    public void destroy() {
        try {
            log.info("====关闭后台任务任务线程池====");
            ThreadUtils.shutdownAndAwaitTermination(scheduledExecutorService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 自定义 @Async 注解使用系统线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        if(EnvironmentUtils.isVirtual()) {
            return new VirtualThreadTaskExecutor("async-");
        }
        return SpringContextHolder.getBean("scheduledExecutorService",ScheduledExecutorService.class);
    }

    /**
     * 异步执行异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message - ").append(throwable.getMessage())
                    .append(", Method name - ").append(method.getName());
            if (ArrayUtils.isNotEmpty(objects)) {
                sb.append(", Parameter value - ").append(Arrays.toString(objects));
            }
            throw new GoyaRuntimeException(sb.toString());
        };
    }
}
