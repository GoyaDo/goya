package com.ysmjjsy.goya.component.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Description: List 常用工具类 </p>
 *
 * @author goya
 * @since 2023/3/28 23:15
 */
@UtilityClass
public class ListUtils {

    /**
     * 将两个已排序的集合a和b合并到一个单独的已排序列表中，以便保留元素的自然顺序。
     *
     * @param appendResources  自定义配置
     * @param defaultResources 默认配置
     * @return 合并后的List
     */
    public static List<String> merge(List<String> appendResources, List<String> defaultResources) {
        if (CollectionUtils.isEmpty(appendResources)) {
            return defaultResources;
        } else {
            return CollectionUtils.collate(defaultResources, appendResources);
        }
    }

    /**
     * 将 List 转换为 String[]
     *
     * @param resources List
     * @return String[]
     */
    public static String[] toStringArray(List<String> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            String[] result = new String[resources.size()];
            return resources.toArray(result);
        } else {
            return new String[]{};
        }
    }

    /**
     * 将字符串数组转换成字符串List
     *
     * @param array 字符串数组
     * @return 字符串List
     */
    public static List<String> toStringList(String[] array) {
        if (org.apache.commons.lang3.ArrayUtils.isNotEmpty(array)) {
            List<String> list = new ArrayList<>(array.length);
            Collections.addAll(list, array);
            return list;
        } else {
            return new ArrayList<>();
        }
    }
}
