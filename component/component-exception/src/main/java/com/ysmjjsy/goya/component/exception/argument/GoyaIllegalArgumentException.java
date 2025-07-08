package com.ysmjjsy.goya.component.exception.argument;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>参数校验异常</p>
 *
 * @author goya
 * @since 2025/6/13 15:56
 */
public class GoyaIllegalArgumentException extends GoyaRuntimeException {
  @Serial
  private static final long serialVersionUID = 1195004901969603103L;

  public GoyaIllegalArgumentException() {
  }

  public GoyaIllegalArgumentException(String message) {
    super(message);
  }

  public GoyaIllegalArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public GoyaIllegalArgumentException(Throwable cause) {
    super(cause);
  }

  public GoyaIllegalArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public ErrorCodeService getErrorCode() {
    return ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION;
  }
}
