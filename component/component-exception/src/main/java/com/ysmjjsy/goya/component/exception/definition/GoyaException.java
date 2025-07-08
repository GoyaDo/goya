package com.ysmjjsy.goya.component.exception.definition;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.response.Response;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 13:59
 */
public interface GoyaException {

    /**
     * 获取反馈信息
     *
     * @return 反馈信息对象 {@link ErrorCodeService}
     */
    ErrorCodeService getErrorCode();

    /**
     * <p>错误信息转换为 Result 对象</p>
     *
     */
    Response<Void> getResponse();
}
