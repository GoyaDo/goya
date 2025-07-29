package com.ysmjjsy.goya.component.exception.request;


import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;

import java.io.Serial;

/**
 * <p>Description: 重复提交Exception </p>
 *
 * @author goya
 * @since 2021/8/25 17:43
 */
public class RepeatSubmissionException extends IllegalOperationException {

    @Serial
    private static final long serialVersionUID = 2598836377094537105L;

    public RepeatSubmissionException() {
    }

    public RepeatSubmissionException(String message) {
        super(message);
    }

    public RepeatSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatSubmissionException(Throwable cause) {
        super(cause);
    }

    public RepeatSubmissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.REPEAT_SUBMISSION;
    }
}
