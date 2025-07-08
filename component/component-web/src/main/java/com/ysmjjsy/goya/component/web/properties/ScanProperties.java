package com.ysmjjsy.goya.component.web.properties;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>Description: 接口扫描配置 </p>
 *
 * @author goya
 * @since 2022/1/16 18:58
 */
@Setter
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_WEB_SCAN)
public class ScanProperties {

    /**
     * 是否开启接口扫描
     */
    @Getter
    private Boolean enabled = true;
    /**
     * 指定扫描的命名空间。未指定的命名空间中，即使包含RequestMapping，也不会被添加进来。
     */
    private List<String> scanGroupIds;
    /**
     * Spring 中会包含 Controller和 RestController，
     * 如果该配置设置为True，那么就只扫描RestController
     * 如果该配置设置为False，那么Controller和 RestController斗扫描。
     */
    @Getter
    private Boolean justScanRestController = false;

    public List<String> getScanGroupIds() {
        List<String> defaultGroupIds = Stream.of(GoyaConstants.PACKAGE_NAME).toList();

        if (CollectionUtils.isEmpty(this.scanGroupIds)) {
            this.scanGroupIds = new ArrayList<>();
        }

        this.scanGroupIds.addAll(defaultGroupIds);
        return scanGroupIds;
    }

}
