package com.ysmjjsy.goya.component.common.constants;

/**
 * <p>默认常量</p>
 *
 * @author goya
 * @since 2025/6/13 10:25
 */
public interface DefaultConstants {

    /**
     * 默认的时间日期格式
     */
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String SUCCESS_CODE = "20000";

    String FAILURE_CODE = "50000";

    int SUCCESS_STATUS = 200;

    int FAILURE_STATUS = 500;

    String SUCCESS_MESSAGE = "success";

    String FAILURE_MESSAGE = "failure";

    String EMPTY_MESSAGE = "empty data";

    String NONE = "none";
    String CODE = "code";

    /**
     * 默认树形结构根节点
     */
    String TREE_ROOT_ID = SymbolConstants.ZERO;

    /**
     * 默认租户ID
     */
    String DEFAULT_TENANT_ID = "public";

    /**
     * 默认用户ID
     */
    String DEFAULT_USER_ID = "public";
}
