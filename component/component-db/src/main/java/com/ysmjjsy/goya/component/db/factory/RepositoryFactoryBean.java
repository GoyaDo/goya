package com.ysmjjsy.goya.component.db.factory;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import com.ysmjjsy.goya.component.db.configuration.DbProperties;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.enums.RepositoryMode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/1 10:02
 */
@RequiredArgsConstructor
public class RepositoryFactoryBean <T extends GoyaRepository<?>> implements FactoryBean<T>, InitializingBean {

    private Class<T> repositoryInterface;

    private T targetRepository;

    private final DbProperties dbProperties;

    @Override
    public T getObject() throws Exception {
        return targetRepository;
    }

    @Override
    public Class<?> getObjectType() {
        return repositoryInterface;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RepositoryMode mode = dbProperties.getMode();
        Map<String, GoyaRepository> repositoryMap = ApplicationContextHolder.getBeansOfType(GoyaRepository.class);
        repositoryMap.forEach((k,v)->{
            if (StringUtils.containsIgnoreCase(k, mode.name())){
                targetRepository = (T) v;
            }
        });
    }
}
