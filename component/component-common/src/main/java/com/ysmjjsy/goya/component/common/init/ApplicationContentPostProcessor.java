package com.ysmjjsy.goya.component.common.init;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>应用初始化后置处理器，防止Spring事件被多次执行</p>
 *
 * @author goya
 * @since 2025/7/24 23:42
 */
public class ApplicationContentPostProcessor implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * 执行标识，确保Spring事件 {@link ApplicationReadyEvent} 有且执行一次
     */
    private boolean executeOnlyOnce = true;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        synchronized (ApplicationContentPostProcessor.class) {
            if (executeOnlyOnce) {
                ApplicationContextHolder.getApplicationContext().publishEvent(new ApplicationInitializingEvent(this));
                executeOnlyOnce = false;
            }
        }
    }
}
