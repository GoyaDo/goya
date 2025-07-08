package com.ysmjjsy.goya.component.event.processor;

import com.ysmjjsy.goya.component.event.annotation.GoyaAnnoEventListener;
import com.ysmjjsy.goya.component.event.core.EventHandleException;
import com.ysmjjsy.goya.component.event.core.GoyaEvent;
import com.ysmjjsy.goya.component.event.core.GoyaEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法事件监听器适配器
 * 将标注@GoyaAnnoEventListener的方法包装为GoyaEventListener接口
 *
 * @author goya
 * @since 2025/6/13 18:00
 */
public class MethodGoyaEventListenerAdapter implements GoyaEventListener<GoyaEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MethodGoyaEventListenerAdapter.class);
    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private final Object targetBean;
    private final Method targetMethod;
    private final GoyaAnnoEventListener annotation;
    private final Expression conditionExpression;

    public MethodGoyaEventListenerAdapter(Object targetBean, Method targetMethod, GoyaAnnoEventListener annotation) {
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        this.annotation = annotation;
        
        // 预编译SpEL表达式
        if (StringUtils.hasText(annotation.condition())) {
            this.conditionExpression = EXPRESSION_PARSER.parseExpression(annotation.condition());
        } else {
            this.conditionExpression = null;
        }
        
        // 确保方法可访问
        this.targetMethod.setAccessible(true);
    }

    @Override
    public void onEvent(GoyaEvent event) throws EventHandleException {
        try {
            // 检查条件表达式
            if (!evaluateCondition(event)) {
                logger.debug("Event {} skipped by condition: {}", event.getEventId(), annotation.condition());
                return;
            }

            logger.debug("Processing event {} with method {}.{}", 
                    event.getEventId(), targetBean.getClass().getSimpleName(), targetMethod.getName());

            // 调用目标方法
            targetMethod.invoke(targetBean, event);

            logger.debug("Event {} processed successfully by method {}.{}", 
                    event.getEventId(), targetBean.getClass().getSimpleName(), targetMethod.getName());

        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof EventHandleException) {
                throw (EventHandleException) cause;
            } else {
                throw new EventHandleException(
                        event.getEventId(),
                        getListenerIdentifier(),
                        "Failed to process event in method listener",
                        cause != null ? cause : e);
            }
        } catch (IllegalAccessException e) {
            throw new EventHandleException(
                    event.getEventId(),
                    getListenerIdentifier(),
                    "Cannot access event listener method",
                    e);
        } catch (Exception e) {
            throw new EventHandleException(
                    event.getEventId(),
                    getListenerIdentifier(),
                    "Unexpected error in event listener method",
                    e);
        }
    }

    @Override
    public int getPriority() {
        return annotation.priority();
    }

    @Override
    public boolean isAsync() {
        return annotation.async();
    }

    /**
     * 评估SpEL条件表达式
     *
     * @param event 事件对象
     * @return 是否满足条件
     */
    private boolean evaluateCondition(GoyaEvent event) {
        if (conditionExpression == null) {
            return true;
        }

        try {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("event", event);
            context.setVariable("headers", event.getHeaders());
            context.setVariable("eventType", event.getEventType());
            context.setVariable("eventId", event.getEventId());

            Object result = conditionExpression.getValue(context);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            logger.warn("Error evaluating condition expression '{}' for event {}: {}", 
                    annotation.condition(), event.getEventId(), e.getMessage());
            return false;
        }
    }

    /**
     * 获取监听器标识符
     */
    private String getListenerIdentifier() {
        return String.format("%s.%s", 
                targetBean.getClass().getSimpleName(), 
                targetMethod.getName());
    }

    /**
     * 获取目标Bean
     */
    public Object getTargetBean() {
        return targetBean;
    }

    /**
     * 获取目标方法
     */
    public Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * 获取注解信息
     */
    public GoyaAnnoEventListener getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return String.format("MethodGoyaEventListenerAdapter{bean=%s, method=%s, async=%s, priority=%d}", 
                targetBean.getClass().getSimpleName(), 
                targetMethod.getName(), 
                annotation.async(), 
                annotation.priority());
    }
} 