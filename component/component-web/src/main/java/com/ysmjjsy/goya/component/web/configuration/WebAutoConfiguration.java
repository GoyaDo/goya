package com.ysmjjsy.goya.component.web.configuration;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.common.strategy.Singleton;
import com.ysmjjsy.goya.component.core.configuration.CoreAutoConfiguration;
import com.ysmjjsy.goya.component.core.context.ServiceContextHolder;
import com.ysmjjsy.goya.component.core.resolver.PropertyResolver;
import com.ysmjjsy.goya.component.doc.configuration.SpringdocAutoConfiguration;
import com.ysmjjsy.goya.component.web.configuration.properties.PlatformProperties;
import com.ysmjjsy.goya.component.web.initializer.GoyaContextHolderBuilder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>Description: Web 服务通用配置 </p>
 *
 * @author goya
 * @since 2024/1/24 16:34
 */
@Slf4j
@AutoConfiguration(before = SpringdocAutoConfiguration.class,after = CoreAutoConfiguration.class)
@EnableConfigurationProperties({PlatformProperties.class})
public class WebAutoConfiguration implements ApplicationContextAware {

    private final ServiceContextHolder serviceContextHolder;

    /**
     * 使用构造函数的方式，可以确保时机正确，几个参数对象设置正确，最终保证 ServiceContextHolder 的初始化时机合理
     *
     * @param platformProperties {@link PlatformProperties}
     * @param serverProperties   {@link ServerProperties}
     */
    public WebAutoConfiguration(PlatformProperties platformProperties, ServerProperties serverProperties) {
        this.serviceContextHolder = GoyaContextHolderBuilder.builder()
                .platformProperties(platformProperties)
                .serverProperties(serverProperties)
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebServiceConfiguration configure.");
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.serviceContextHolder.setApplicationContext(applicationContext);
        this.serviceContextHolder.setApplicationName(PropertyResolver.getProperty(applicationContext.getEnvironment(), GoyaConstants.ITEM_SPRING_APPLICATION_NAME));
        log.trace("[Goya] |- component [web] |- Goya ApplicationContext initialization completed.");
        Singleton.put(this.serviceContextHolder);
    }

}
