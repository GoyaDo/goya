package com.ysmjjsy.goya.component.pojo.enums;

/**
 * <p> Description : 用于区分是单体应用还是微服务应用 </p>
 *
 * @author goya
 * @since 2019/11/26 11:33
 */
public enum Architecture implements GoyaBaseEnum{

    /**
     * 分布式架构
     */
    DISTRIBUTED,

    /**
     * 单体式架构
     */
    MONOCOQUE;
}
