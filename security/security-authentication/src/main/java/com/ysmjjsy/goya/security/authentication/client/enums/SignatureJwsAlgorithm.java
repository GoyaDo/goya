package com.ysmjjsy.goya.security.authentication.client.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.ysmjjsy.goya.component.dto.enums.GoyaEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: OAuth2 Signature </p>
 * <p>
 * 由JSON Web算法（JWA）规范定义的加密算法的枚举，并由JSON Web签名（JWS）用于对JWS受保护标头和JWS有效载荷的内容进行数字签名。
 *
 * @author goya
 * @since 2022/3/2 16:15
 */
@Schema(name = "签名算法")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SignatureJwsAlgorithm implements GoyaEnum<Integer> {

    /**
     * RSASSA-PKCS1-v1_5 using SHA-256 (Recommended)
     */
    RS256(0, "签名算法 RS256"),
    /**
     * RSASSA-PKCS1-v1_5 using SHA-384 (Optional)
     */
    RS384(1, "签名算法RS384"),
    /**
     * RSASSA-PKCS1-v1_5 using SHA-512 (Optional)
     */
    RS512(2, "签名算法 RS512"),
    /**
     * ECDSA using P-256 and SHA-256 (Recommended+)
     */
    ES256(3, "签名算法 ES256"),
    /**
     * ECDSA using P-384 and SHA-384 (Optional)
     */
    ES384(4, "签名算法 ES384"),
    /**
     * ECDSA using P-521 and SHA-512 (Optional)
     */
    ES512(5, "签名算法 ES512"),
    /**
     * RSASSA-PSS using SHA-256 and MGF1 with SHA-256 (Optional)
     */
    PS256(6, "签名算法 PS256"),
    /**
     * RSASSA-PSS using SHA-384 and MGF1 with SHA-384 (Optional)
     */
    PS384(7, "签名算法 PS384"),
    /**
     * RSASSA-PSS using SHA-512 and MGF1 with SHA-512 (Optional)
     */
    PS512(8, "签名算法 PS512");

    private static final Map<Integer, SignatureJwsAlgorithm> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (SignatureJwsAlgorithm signatureJwsAlgorithm : SignatureJwsAlgorithm.values()) {
            INDEX_MAP.put(signatureJwsAlgorithm.getValue(), signatureJwsAlgorithm);
            JSON_STRUCTURE.add(signatureJwsAlgorithm.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", signatureJwsAlgorithm.getValue())
                            .put("key", signatureJwsAlgorithm.name())
                            .put("text", signatureJwsAlgorithm.getDescription())
                            .put("index", signatureJwsAlgorithm.getValue())
                            .build());
        }
    }

    @Schema(title = "枚举值")
    private final Integer value;
    @Schema(name = "文字")
    private final String description;

    SignatureJwsAlgorithm(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static SignatureJwsAlgorithm get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum枚举值
     */
    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
