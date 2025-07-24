package com.ysmjjsy.goya.component.distributedid.core.snowflake;

import com.ysmjjsy.goya.component.distributedid.utils.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>雪花算法模板生成</p>
 *
 * @author goya
 * @since 2025/7/25 00:33
 */
@Slf4j
public abstract class AbstractWorkIdChooseTemplate {

    /**
     * 根据自定义策略获取 WorkId 生成器
     *
     * @return
     */
    protected abstract WorkIdWrapper chooseWorkId();

    /**
     * 选择 WorkId 并初始化雪花
     */
    public void chooseAndInit() {
        // 模板方法模式: 通过抽象方法获取 WorkId 包装器创建雪花算法
        WorkIdWrapper workIdWrapper = chooseWorkId();
        long workId = workIdWrapper.getWorkId();
        long dataCenterId = workIdWrapper.getDataCenterId();
        Snowflake snowflake = new Snowflake(workId, dataCenterId, true);
        log.info("Snowflake type: {}, workId: {}, dataCenterId: {}", this.getClass().getSimpleName(), workId, dataCenterId);
        SnowflakeIdUtil.initSnowflake(snowflake);
    }
}
