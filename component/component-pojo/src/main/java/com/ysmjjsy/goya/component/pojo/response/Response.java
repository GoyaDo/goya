package com.ysmjjsy.goya.component.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ysmjjsy.goya.component.pojo.constants.DefaultConstants;
import com.ysmjjsy.goya.component.pojo.domain.DTO;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>统一响应返回实体</p>
 *
 * @author goya
 * @since 2025/6/13 10:23
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Schema(title = "统一响应返回实体", description = "所有接口统一返回的实体定义", example = "Response.ok()")
public class Response<T> implements DTO {

    @Serial
    private static final long serialVersionUID = -5342623454580268014L;

    @Schema(title = "响应时间戳", pattern = DefaultConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = DefaultConstants.DATE_TIME_FORMAT)
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(title = "响应结果")
    @JsonProperty("success")
    private final boolean success;

    @Schema(title = "自定义响应编码")
    @JsonProperty("code")
    private final String code;

    @Schema(title = "响应返回信息")
    @JsonProperty("message")
    private final String message;

    @Schema(title = "响应数据")
    private final T data;

    @Schema(title = "http状态码")
    private final int status;

    @Schema(title = "请求路径")
    private String path;

    @Schema(title = "链路追踪TraceId")
    private String traceId;


    protected Response(String code, String message, int status) {
        this(code, message, status, null, null);
    }

    protected Response(String code, String message, int status, String traceId) {
        this(code, message, status, traceId, null);
    }

    protected Response(String code, String message, int status, String traceId, T data) {
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        } else {
            this.message = DefaultConstants.SUCCESS_MESSAGE;
        }
        this.code = code;
        this.status = status;
        this.traceId = traceId;
        this.success = DefaultConstants.SUCCESS_CODE.equals(code) && DefaultConstants.SUCCESS_STATUS == status;
        this.data = data;
    }

    public Response<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public Response<T> path(String path) {
        this.path = path;
        return this;
    }

    public static <T> Response<T> empty() {
        return empty(DefaultConstants.EMPTY_MESSAGE);
    }

    public static <T> Response<T> empty(String message) {
        return empty(message, ErrorCodes.NO_CONTENT.getCode());
    }

    public static <T> Response<T> empty(String message, String code) {
        return empty(code, message, ErrorCodes.NO_CONTENT.getStatus());
    }

    public static <T> Response<T> empty(String code, String message, int status) {
        return new Response<>(code, message, status, null);
    }

    public static <T> Response<T> success() {
        return success(DefaultConstants.SUCCESS_MESSAGE);
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(DefaultConstants.SUCCESS_CODE, message, DefaultConstants.SUCCESS_STATUS);
    }

    public static Response<Void> failure() {
        return failure(DefaultConstants.FAILURE_MESSAGE);
    }

    public static Response<Void> failure(String message) {
        return failure(DefaultConstants.FAILURE_CODE, message);
    }

    public static Response<Void> failure(String code, String message) {
        return new Response<>(code, message, DefaultConstants.FAILURE_STATUS);
    }

    public static Response<Void> failure(String code, String message, int status) {
        return new Response<>(code, message, status);
    }

    public static Response<Void> failure(ErrorCodeService errorCode) {
        return failure(errorCode, errorCode.getDescription());
    }

    public static <T> Response<T> data(T data) {
        return data(DefaultConstants.SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> data(String message, T data) {
        return new Response<>(DefaultConstants.SUCCESS_CODE, message, DefaultConstants.SUCCESS_STATUS, "", data);
    }

    public static Response<Void> failure(ErrorCodeService errorCodeService, String message) {
        return new Response<>(errorCodeService.getCode(), message, errorCodeService.getStatus());
    }
}
