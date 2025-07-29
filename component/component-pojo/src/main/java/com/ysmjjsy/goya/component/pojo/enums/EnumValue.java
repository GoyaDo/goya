package com.ysmjjsy.goya.component.pojo.enums;

/**
 * <p>Description: 枚举值定义 </p>
 *
 * @author goya
 * @since : 2022/3/26 16:49
 */
public interface EnumValue<T> {

    /**
     * 获取枚举自定义值
     *
     * @return 自定义枚举值
     */
    T getValue();
}
