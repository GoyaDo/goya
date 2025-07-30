package com.ysmjjsy.goya.module.tenant;

import com.ysmjjsy.goya.component.pojo.constants.DefaultConstants;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * <p>Description: Schema 模式多租户 ConnectionProvider </p>
 *
 * @author goya
 * @since 2023/3/28 22:40
 */

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(SchemaMultiTenantConnectionProvider.class);
    @Serial
    private static final long serialVersionUID = 9109392361795243828L;

    private final DataSource dataSource;

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String schema) throws SQLException {
        final Connection connection = getAnyConnection();
        connection.setSchema(schema);
        log.debug("[Goya] |- Get connection for schema tenant [{}]", schema);
        return connection;
    }

    @Override
    public void releaseConnection(String schema, Connection connection) throws SQLException {
        connection.setSchema(DefaultConstants.DEFAULT_TENANT_ID);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
