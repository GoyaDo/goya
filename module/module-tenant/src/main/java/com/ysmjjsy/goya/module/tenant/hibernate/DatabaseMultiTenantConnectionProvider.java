package com.ysmjjsy.goya.module.tenant.hibernate;

import com.ysmjjsy.goya.module.tenant.datasource.MultiTenantDataSourceFactory;
import com.ysmjjsy.goya.component.pojo.constants.DefaultConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 数据库连接提供者 </p>
 * <p>
 * 通过该类明确，在租户系统中具体使用的是哪个 Database 或 Schema
 *
 * @author goya
 * @since 2022/9/8 18:14
 */
public class DatabaseMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> implements HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseMultiTenantConnectionProvider.class);
    private final Map<String, DataSource> dataSources = new HashMap<>();
    private final DataSource defaultDataSource;
    private boolean isDataSourceInit = false;

    public DatabaseMultiTenantConnectionProvider(DataSource dataSource) {
        this.defaultDataSource = dataSource;
        dataSources.put(DefaultConstants.DEFAULT_TENANT_ID, dataSource);
    }

    private void initialize() {
        isDataSourceInit = true;
        MultiTenantDataSourceFactory factory = SpringUtil.getBean(MultiTenantDataSourceFactory.class);
        dataSources.putAll(factory.getAll(defaultDataSource));
    }

    /**
     * 在没有指定 tenantId 的情况下选择的数据源（例如启动处理）
     *
     * @return {@link DataSource}
     */
    @Override
    protected DataSource selectAnyDataSource() {
        log.debug("[Goya] |- Select any dataSource: " + defaultDataSource);
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!isDataSourceInit) {
            initialize();
        }

        DataSource currentDataSource = dataSources.get(tenantIdentifier);
        if (ObjectUtils.isNotEmpty(currentDataSource)) {
            log.debug("[Goya] |- Found the multi tenant dataSource for id : [{}]", tenantIdentifier);
            return currentDataSource;
        } else {
            log.warn("[Goya] |- Cannot found the dataSource for tenant [{}], change to use default.", tenantIdentifier);
            return defaultDataSource;
        }
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
