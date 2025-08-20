package com.ysmjjsy.goya.component.distributedid.handler;

import com.ysmjjsy.goya.component.distributedid.core.IdGenerator;
import com.ysmjjsy.goya.component.distributedid.core.serviceid.DefaultServiceIdGenerator;
import com.ysmjjsy.goya.component.distributedid.core.serviceid.ServiceIdGenerator;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>ID 生成器管理</p>
 *
 * @author goya
 * @since 2025/7/25 00:31
 */
@UtilityClass
public class IdGeneratorManager {

    /**
     * ID 生成器管理容器
     */
    private static Map<String, IdGenerator> MANAGER = new ConcurrentHashMap<>();

    /*
      注册默认 ID 生成器
     */
    static {
        MANAGER.put("default", new DefaultServiceIdGenerator());
    }

    /**
     * 注册 ID 生成器
     */
    public static void registerIdGenerator(@NonNull String resource, @NonNull IdGenerator idGenerator) {
        IdGenerator actual = MANAGER.get(resource);
        if (actual != null) {
            return;
        }
        MANAGER.put(resource, idGenerator);
    }

    /**
     * 根据 {@param resource} 获取 ID 生成器
     */
    public static IdGenerator getIdGenerator(@NonNull String resource) {
        return MANAGER.get(resource);
    }

    /**
     * 获取默认 ID 生成器 {@link DefaultServiceIdGenerator}
     */
    public static ServiceIdGenerator getDefaultServiceIdGenerator() {
        return Optional.ofNullable(MANAGER.get("default")).map(ServiceIdGenerator.class::cast).orElse(null);
    }
}
