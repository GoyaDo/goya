package com.ysmjjsy.goya.component.common.pool;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

/**
 * <p>Description: 对象池通用配置参数实体 </p>
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
@Setter
@Getter
public class Pool {

    /**
     * 池中的最大对象数
     */
    private Integer maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

    /**
     * 最多的空闲对象数
     */
    private Integer maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

    /**
     * 最多的空闲对象数
     */
    private Integer minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

    /**
     * 对象池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后
     */
    private Boolean lifo = true;

    /**
     * 当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常
     */
    private Duration maxWait = BaseObjectPoolConfig.DEFAULT_MAX_WAIT;

    /**
     * 对象池满了，是否阻塞获取（false则借不到直接抛异常）, 默认 true
     */
    private Boolean blockWhenExhausted = BaseObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED;

    /**
     * 空闲的最小时间,达到此值后空闲连接可能会被移除, 默认30分钟
     */
    private Duration minEvictableIdleDuration = BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_DURATION;

    private Duration softMinEvictableIdleDuration = BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_DURATION;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxTotal", maxTotal)
                .add("maxIdle", maxIdle)
                .add("minIdle", minIdle)
                .add("lifo", lifo)
                .add("maxWait", maxWait)
                .add("blockWhenExhausted", blockWhenExhausted)
                .add("minEvictableIdleTime", minEvictableIdleDuration)
                .add("softMinEvictableIdleTime", softMinEvictableIdleDuration)
                .toString();
    }
}
