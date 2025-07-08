package com.ysmjjsy.goya.component.exception.properties;


import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: Property 属性值没有设置错误 </p>
 *
 * @author goya
 * @since 2022/3/6 13:57
 */
public class PropertyValueIsNotSetException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 7645646796569860066L;

    public PropertyValueIsNotSetException() {
        super();
    }

    public PropertyValueIsNotSetException(String message) {
        super(message);
    }

    public PropertyValueIsNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyValueIsNotSetException(Throwable cause) {
        super(cause);
    }

    protected PropertyValueIsNotSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.PROPERTY_VALUE_IS_NOT_SET_EXCEPTION;
    }
}
