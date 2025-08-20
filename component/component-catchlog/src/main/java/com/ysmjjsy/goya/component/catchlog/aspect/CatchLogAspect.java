package com.ysmjjsy.goya.component.catchlog.aspect;

import cn.hutool.v7.json.JSONUtil;
import com.ysmjjsy.goya.component.catchlog.handler.ResponseHandlerFactory;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaAbstractRuntimeException;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaException;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:37
 */
@Aspect
@Slf4j
@Order(1)
public class CatchLogAspect {

    /**
     * <a href="https://blog.csdn.net/zhengchao1991/article/details/53391244">The syntax of pointcut </a>
     */
    @Pointcut("@within(com.ysmjjsy.goya.component.catchlog.annotation.CatchAndLog) && execution(public * *(..))")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();

        logRequest(joinPoint);

        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (Throwable e) {
            response = handleException(joinPoint, e);
        } finally {
            logResponse(startTime, response);
        }

        return response;
    }

    private Object handleException(ProceedingJoinPoint joinPoint, Throwable e) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = ms.getReturnType();

        if (e instanceof GoyaAbstractRuntimeException) {
            log.warn("goya run exception : {}", e.getMessage());
            // 在Debug的时候，对于BizException也打印堆栈
            if (log.isDebugEnabled()) {
                log.error(e.getMessage(), e);
            }
            return ResponseHandlerFactory.get().handle(returnType,
                    ((GoyaRuntimeException) e).getErrorCode().getCode(), e.getMessage());
        } else if (e instanceof GoyaException) {
            log.error("goya exception: {}", e.getMessage(), e);
            return ResponseHandlerFactory.get().handle(returnType,
                    ((GoyaException) e).getErrorCode().getCode(), e.getMessage());
        }

        log.error("UNKNOWN EXCEPTION: {}", e.getMessage(), e);
        return ResponseHandlerFactory.get().handle(returnType, "UNKNOWN_ERROR", e.getMessage());
    }


    private void logResponse(long startTime, Object response) {
        try {
            long endTime = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("RESPONSE: {}", JSONUtil.toJsonStr(response));
                log.debug("COST: {}ms", (endTime - startTime));
            }
        } catch (Exception e) {
            //swallow it
            log.error("logResponse error: {}", e.getMessage(), e);
        }
    }

    private void logRequest(ProceedingJoinPoint joinPoint) {
        if (!log.isDebugEnabled()) {
            return;
        }

        try {
            log.debug("START PROCESSING: {}", joinPoint.getSignature().toShortString());
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                log.debug("REQUEST: {}", JSONUtil.toJsonStr(arg));
            }
        } catch (Exception e) {
            //swallow it
            log.error("logReqeust error: {}", e.getMessage(), e);
        }
    }
}
