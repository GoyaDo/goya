package com.ysmjjsy.goya.module.redis.configuration.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.common.constants.SymbolConstants;
import com.ysmjjsy.goya.component.common.pojo.enums.Protocol;
import com.ysmjjsy.goya.module.redis.constants.RedisConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Redisson 配置 </p>
 *
 * @author goya
 * @since 2021/10/22 14:02
 */
@Data
@ConfigurationProperties(prefix = RedisConstants.PROPERTY_PREFIX_REDISSON)
public class RedissonProperties {

    /**
     * 是否开启 Redisson
     */
    private Boolean enabled = false;
    /**
     * Redis 模式
     */
    private Mode mode = Mode.SINGLE;
    /**
     * 是否使用 SSL 连接。false，协议头为 redis://, true 协议头为 rediss://
     */
    private Boolean useSslConnection = false;
    /**
     * 配置文件路径
     */
    private String config;
    /**
     * 单体配置
     */
    private SingleServerConfig singleServerConfig;
    /**
     * 集群配置
     */
    private ClusterServersConfig clusterServersConfig;
    /**
     * 哨兵配置
     */
    private SentinelServersConfig sentinelServersConfig;

    public String getProtocol() {
        return Boolean.TRUE.equals(getUseSslConnection()) ? Protocol.REDISS.getFormat() : Protocol.REDIS.getFormat();
    }

    public boolean isExternalConfig() {
        return StringUtils.isNotBlank(this.getConfig());
    }

    public boolean isYamlConfig() {
        if (this.isExternalConfig()) {
            return StringUtils.endsWithIgnoreCase(this.getConfig(), SymbolConstants.SUFFIX_YAML);
        } else {
            return false;
        }
    }

    public boolean isJsonConfig() {
        if (this.isExternalConfig()) {
            return StringUtils.endsWithIgnoreCase(this.getConfig(), SymbolConstants.SUFFIX_JSON);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("mode", mode)
                .add("config", config)
                .toString();
    }

    /**
     * Redisson 使用模式
     */
    public enum Mode {
        /**
         * 单机
         */
        SINGLE,
        /**
         * 哨兵
         */
        SENTINEL,
        /**
         * 集群
         */
        CLUSTER
    }
}
