package com.ysmjjsy.goya.component.pojo.errorcode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ysmjjsy.goya.component.pojo.constants.DefaultConstants;
import lombok.AllArgsConstructor;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 13:29
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCodes implements ErrorCodeService {


    /**
     * 200
     */
    SUCCESS("20000", DefaultConstants.SUCCESS_STATUS, DefaultConstants.SUCCESS_MESSAGE),

    /**
     * 204
     */
    NO_CONTENT("20400", 204, "无内容"),

    /**
     * 401.** 未经授权 Unauthorized	请求要求用户的身份认证
     */
    UNAUTHORIZED("40100", 401, "未经授权"),
    ACCESS_DENIED("40101", 401, "您没有权限，拒绝访问"),
    INVALID_CLIENT("40102", 401, "客户端身份验证失败或数据库未初始化"),
    INVALID_GRANT("40103", 401, "客户端身份验证失败或数据库未初始化"),
    INVALID_TOKEN("40104", 401, "提供的访问令牌已过期、吊销、格式错误或无效"),
    UNAUTHORIZED_CLIENT("40105", 401, "客户端无权使用此方法请求授权码或访问令牌"),
    ACCOUNT_EXPIRED("40106", 401, "该账户已经过期"),
    BAD_CREDENTIALS("40107", 401, "用户名或密码错误"),
    CREDENTIALS_EXPIRED("40108", 401, "该账户密码凭证已过期"),
    ACCOUNT_DISABLED("40109", 401, "该账户已经被禁用"),
    ACCOUNT_LOCKED("40110", 401, "该账户已经被锁定"),
    ACCOUNT_ENDPOINT_LIMITED("40111", 401, "您已经使用其它终端登录,请先退出其它终端"),
    USERNAME_NOT_FOUND("40112", 401, "用户名或密码错误"),
    SESSION_EXPIRED("40113", 401, "Session 已过期，请刷新页面后再使用"),
    NOT_AUTHENTICATED("40114", 401, "请求的地址未通过身份认证"),

    /**
     * 403.** 禁止的请求，与403对应
     */
    FORBIDDEN("40300", 403, "禁止的请求"),
    INSUFFICIENT_SCOPE("40301", 403, "TOKEN权限不足，您需要更高级别的权限"),
    SQL_INJECTION_REQUEST("40302", 403, "疑似SQL注入请求"),


    /**
     * 405.** 方法不允许 与405对应
     */
    METHOD_NOT_ALLOWED("40500", 405, "方法不允许"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("40501", 405, "请求使用的方法类型不支持"),


    /**
     * 406.** 不接受的请求，与406对应
     */
    NOT_ACCEPTABLE("40600", 406, "不接受的请求"),
    UNSUPPORTED_GRANT_TYPE("40601", 406, "授权服务器不支持授权授予类型"),
    UNSUPPORTED_RESPONSE_TYPE("40602", 406, "授权服务器不支持使用此方法获取授权代码或访问令牌"),
    UNSUPPORTED_TOKEN_TYPE("40603", 406, "授权服务器不支持撤销提供的令牌类型"),
    USERNAME_ALREADY_EXISTS("40604", 406, "用户名已经存在"),
    FEIGN_DECODER_IO_EXCEPTION("40605", 406, "Feign 解析 Fallback 错误信息出错"),
    CAPTCHA_CATEGORY_IS_INCORRECT("40606", 406, "验证码分类错误"),
    CAPTCHA_HANDLER_NOT_EXIST("40607", 406, "验证码处理器不存在"),
    CAPTCHA_HAS_EXPIRED("40608", 406, "验证码已过期"),
    CAPTCHA_IS_EMPTY("40609", 406, "验证码不能为空"),
    CAPTCHA_MISMATCH("40610", 406, "验证码不匹配"),
    CAPTCHA_PARAMETER_ILLEGAL("40611", 406, "验证码参数格式错误"),
    SESSION_INVALID("40612", 406, "Session已过期，请刷新再试"),
    REPEAT_SUBMISSION("40613", 406, "提交进行中，请不要重复提交"),
    FREQUENT_REQUESTS("40614", 406, "请求频繁，请稍后再试"),

    /**
     * 412.* 未经授权 Precondition Failed 客户端请求信息的先决条件错误
     */
    PRECONDITION_FAILED("41200", 412, "客户端请求信息的先决条件错误"),
    INVALID_REDIRECT_URI("41201", 412, "OAuth2 URI 重定向的值无效"),
    INVALID_REQUEST("41202", 412, "无效的请求，参数使用错误或无效."),
    INVALID_SCOPE("41203", 412, "授权范围错误"),
    METHOD_ARGUMENT_NOT_VALID("41204", 412, "接口参数校验失败，参数使用错误或者未接收到参数"),
    ACCESS_IDENTITY_VERIFICATION_FAILED("41205", 412, "接入身份认证错误"),
    ACCESS_CONFIG_ERROR("41206", 412, "模块配置错误"),
    ACCESS_HANDLER_NOT_FOUND("41207", 412, "模块接入处理器未找到错误"),
    ACCESS_PRE_PROCESS_FAILED_EXCEPTION("41208", 412, "接入预操作失败错误"),
    ILLEGAL_ACCESS_ARGUMENT("41209", 412, "社交登录参数错误"),
    ILLEGAL_ACCESS_SOURCE("41210", 412, "社交登录Source参数错误"),

    /**
     * 415.* Unsupported Media Type	服务器无法处理请求附带的媒体格式
     */
    UNSUPPORTED_MEDIA_TYPE("41500", 415, "服务器无法处理请求附带的媒体格式"),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE("41501", 415, "不支持的 Media Type"),


    /**
     * 500
     */
    INTERNAL_SERVER_ERROR("50000", DefaultConstants.FAILURE_STATUS, DefaultConstants.FAILURE_MESSAGE),
    SERVER_ERROR("50001", 500, "授权服务器遇到意外情况，无法满足请求"),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION("50002", 500, "JSON字符串反序列化为实体出错！"),
    ILLEGAL_ARGUMENT_EXCEPTION("50003", 500, "参数不合法错误，请仔细确认参数使用是否正确。"),
    IO_EXCEPTION("50004", 500, "IO异常"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION("50005", 500, "接口参数使用错误或必要参数缺失，请查阅接口文档！"),
    NULL_POINTER_EXCEPTION("50006", 500, "后台代码执行过程中出现了空值"),
    TYPE_MISMATCH_EXCEPTION("50007", 500, "类型不匹配"),
    BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION("50008", 500, "从对象池中获取对象错误"),
    OPENAPI_INVOKING_FAILED("50009", 500, "OPEN API 调用失败，请检查API调用方式及参数是否正确"),
    CACHE_INFRASTRUCTURE_ERROR("50010", 500, "缓存操作出现错误"),
    EVENT_INFRASTRUCTURE_ERROR("50011", 500, "事件操作出现错误"),

    /**
     * 501. Not Implemented	服务器不支持请求的功能，无法完成请求
     */
    NOT_IMPLEMENTED("50100", 501, "服务器不支持请求的功能，无法完成请求"),
    DISCOVERED_UNRECORDED_ERROR_EXCEPTION("50101", 501, "发现尚未记录的 Exception 错误,建议在错误体系中添加或者提 ISSUE 给原作者"),
    PROPERTY_VALUE_IS_NOT_SET_EXCEPTION("50102", 501, "必要的Property配置属性值没有设置"),
    URL_FORMAT_INCORRECT_EXCEPTION("50103", 501, "URL格式错误或者缺少Http协议头"),
    ILLEGAL_SYMMETRIC_KEY("50104", 501, "静态AES加密算法KEY非法"),

    /**
     * 503.* Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     */
    SERVICE_UNAVAILABLE("50300", 503, "由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中"),
    COOKIE_THEFT("50301", 503, "Cookie 信息不安全"),
    INVALID_COOKIE("50302", 503, "不可用的 Cookie 信息"),
    PROVIDER_NOT_FOUND("50303", 503, "授权服务器代码逻辑配置错误"),
    TEMPORARILY_UNAVAILABLE("50304", 503, "由于服务器临时超载或维护，授权服务器当前无法处理该请求"),
    SEARCH_IP_LOCATION("50305", 503, "搜索 IP 定位出现读取错误，服务器当前无法处理该请求"),
    OPEN_API_REQUEST_FAILURE("50306", 503, "基础设施 Open Api 调用请求失败"),

    /**
     * 6*.* 为数据操作相关错误
     */
    TRANSACTION_ROLLBACK("60100", 601, "数据事务处理失败，数据回滚"),
    BAD_SQL_GRAMMAR("60101", 601, "低级SQL语法错误，检查SQL能否正常运行或者字段名称是否正确"),
    DATA_INTEGRITY_VIOLATION("60102", 601, "该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作"),


    /**
     * 7*.* 基础设施交互错误
     */
    BASE_INFRASTRUCTURE_ERROR("70000", 700, "基础设施交互错误"),
    REDIS_INFRASTRUCTURE_ERROR("71000", 710, "Redis 操作出现错误"),

    ;

    @JsonValue
    private final String value;

    private final int status;

    private final String description;

    @Override
    public String getCode() {
        return value;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
