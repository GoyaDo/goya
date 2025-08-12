package com.ysmjjsy.goya.component.common.utils;

import lombok.experimental.UtilityClass;

/**
 * <p>Description: 数字类型工具类 </p>
 *
 * @author goya
 * @since 2023/11/1 21:26
 */
@UtilityClass
public class NumberUtils {

    /**
     * long 转 int
     *
     * @param value long 型数值
     * @return int 型数值
     */
    public static int longToInt(long value) {
        return Long.valueOf(value).intValue();
    }
}
