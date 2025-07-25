package com.ysmjjsy.goya.component.common.strategy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * <p>单例对象容器</p>
 *
 * @author goya
 * @since 2025/7/24 23:44
 */
@SuppressWarnings("all")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Singleton {

    private static final ConcurrentHashMap<String, Object> SINGLE_OBJECT_POOL = new ConcurrentHashMap<>();

    /**
     * 根据 key 获取单例对象
     *
     * @param key key
     * @param <T> 对象
     * @return 对象
     */
    public static <T> T get(String key) {
        Object result = SINGLE_OBJECT_POOL.get(key);
        return result == null ? null : (T) result;
    }

    /**
     * 根据 key 获取单例对象
     *
     * <p> 为空时，通过 supplier 构建单例对象并放入容器
     *
     * @param key key
     * @param supplier supplier
     * @param <T> 对象
     * @return 对象
     */
    public static <T> T get(String key, Supplier<T> supplier) {
        Object result = SINGLE_OBJECT_POOL.get(key);
        if (result == null && (result = supplier.get()) != null) {
            SINGLE_OBJECT_POOL.put(key, result);
        }
        return result != null ? (T) result : null;
    }

    /**
     * 对象放入容器
     *
     * @param value 对象
     */
    public static void put(Object value) {
        put(value.getClass().getName(), value);
    }

    /**
     * 对象放入容器
     *
     * @param key key
     * @param value 对象
     */
    public static void put(String key, Object value) {
        SINGLE_OBJECT_POOL.put(key, value);
    }
}
