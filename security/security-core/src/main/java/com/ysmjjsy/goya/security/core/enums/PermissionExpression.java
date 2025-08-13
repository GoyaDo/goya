package com.ysmjjsy.goya.security.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.ysmjjsy.goya.component.common.pojo.enums.GoyaEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 安全表达式 </p>
 *
 * @author goya
 * @since 2025/7/8 22:46
 */
@Schema(name = "Security 权限表达式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PermissionExpression implements GoyaEnum<String> {
    /**
     * 权限表达式
     */
    PERMIT_ALL("permitAll", "permitAll"),
    ANONYMOUS("isAnonymous", "isAnonymous"),
    REMEMBER_ME("isRememberMe", "isRememberMe"),
    DENY_ALL("denyAll", "denyAll"),
    AUTHENTICATED("isAuthenticated", "isAuthenticated"),
    FULLY_AUTHENTICATED("isFullyAuthenticated", "isFullyAuthenticated");

    private static final Map<String, PermissionExpression> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (PermissionExpression permissionExpression : PermissionExpression.values()) {
            INDEX_MAP.put(permissionExpression.getValue(), permissionExpression);
            JSON_STRUCTURE.add(permissionExpression.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", permissionExpression.getValue())
                            .put("key", permissionExpression.name())
                            .put("text", permissionExpression.getDescription())
                            .put("index", permissionExpression.ordinal())
                            .build());
        }
    }

    @Schema(name = "索引")
    private final String value;
    @Schema(name = "说明")
    private final String description;

    PermissionExpression(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PermissionExpression get(String value) {
        return INDEX_MAP.get(value);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
