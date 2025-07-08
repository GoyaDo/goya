package com.ysmjjsy.goya.module.ip2region.configuration;

import com.ysmjjsy.goya.module.ip2region.definition.Ip2RegionSearcher;
import com.ysmjjsy.goya.module.ip2region.properties.Ip2RegionProperties;
import com.ysmjjsy.goya.module.ip2region.searcher.DefaultIp2RegionSearcher;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: Ip2Region 配置 </p>
 *
 * @author goya
 * @since 2023/10/24 11:59
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(Ip2RegionProperties.class)
public class Ip2RegionConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [ip2region] configure.");
    }

    @Bean
    public Ip2RegionSearcher defaultIp2RegionSearcher(Ip2RegionProperties ip2RegionProperties) {
        DefaultIp2RegionSearcher searcher = new DefaultIp2RegionSearcher(ip2RegionProperties.getIpV4Resource(), ip2RegionProperties.getIpV6Resource());
        log.trace("[Goya] |- Bean [Ip2Region Searcher] Configure.");
        return searcher;
    }

}
