package com.ysmjjsy.goya.component.common.mapstruct;

/**
 * <p>一个源对象映射为两个目标对象的通用接口</p>
 *
 * @author goya
 * @since 2025/7/25 00:10
 */
public interface BaseMsMultiMapper <S, T1, T2> {

    T1 toTarget1(S source);

    T2 toTarget2(S source);
}