package com.ysmjjsy.goya.component.web.configuration.properties;

import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>Description: 接口扫描配置 </p>
 *
 * @author goya
 * @since 2022/1/16 18:58
 */
@Setter
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_WEB_SCAN)
@RequiredArgsConstructor
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

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<String> getScanGroupIds() {
        List<String> defaultGroupIds = Stream.of(GoyaConstants.PACKAGE_NAME).toList();

        if (CollectionUtils.isEmpty(this.scanGroupIds)) {
            this.scanGroupIds = new ArrayList<>();
        }

        this.scanGroupIds.addAll(defaultGroupIds);

        // 增加启动类
        String mainPackage = getMainPackage();
        if (StringUtils.isNotEmpty(mainPackage) && !scanGroupIds.contains(mainPackage)) {
                this.scanGroupIds.add(mainPackage);
            }

        return scanGroupIds;
    }

    /**
     * 获取主启动类（含@SpringBootApplication注解）的包名
     */
    public String getMainPackage() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);

        if (beans.isEmpty()) {
            beans = applicationContext.getBeansWithAnnotation(SpringBootConfiguration.class);
        }

        if (!beans.isEmpty()) {
            Class<?> mainClass = beans.values().iterator().next().getClass();
            return mainClass.getPackage().getName();
        }
        return null;
    }
}
