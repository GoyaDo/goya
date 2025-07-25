package com.ysmjjsy.goya.component.common.context;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:31
 */
@Slf4j
public class ApplicationContextHolder implements ApplicationContextAware {
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    /**
     * 通过class获取 Bean.
     *
     * @param targetClz 目标类型
     * @param <T> 类型
     * @return Bean
     */
    public static <T> T getBean(Class<T> targetClz) {
        T beanInstance = null;
        //优先按type查
        try {
            beanInstance = applicationContext.getBean(targetClz);
        } catch (Exception e) {
        }
        //按name查
        try {
            if (beanInstance == null) {
                String simpleName = targetClz.getSimpleName();
                //首字母小写
                simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
                beanInstance = (T) applicationContext.getBean(simpleName);
            }
        }
        catch (Exception e) {
            log.warn("No bean found for " + targetClz.getCanonicalName());
        }
        return beanInstance;
    }

    /**
     * 获取Bean
     *
     * @param clazz bean name
     * @return bean
     */
    public static Object getBean(String clazz) {
        return ApplicationContextHolder.applicationContext.getBean(clazz);
    }

    /**
     * 获取Bean
     *
     * @param name bean name
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return ApplicationContextHolder.applicationContext.getBean(name, requiredType);
    }

    /**
     * 获取Bean
     *
     * @param requiredType bean name
     * @return bean
     */
    public static <T> T getBean(Class<T> requiredType, Object... params) {
        return ApplicationContextHolder.applicationContext.getBean(requiredType, params);
    }

    /**
     * 获取Bean
     *
     * @param clazz bean name
     * @return bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return ApplicationContextHolder.getApplicationContext().getBeansOfType(clazz);
    }

    /**
     * 获取Bean
     *
     * @param beanName bean name
     * @return bean
     */
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
        return ApplicationContextHolder.getApplicationContext().findAnnotationOnBean(beanName, annotationType);
    }
}
