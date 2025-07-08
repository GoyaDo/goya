package com.ysmjjsy.goya.component.event.processor;

import com.ysmjjsy.goya.component.event.core.GoyaEvent;
import com.ysmjjsy.goya.component.event.core.GoyaEventListener;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 事件类型推断工具类
 *
 * @author goya
 * @since 2025/6/13 18:00
 */
public class EventTypeResolver {

    /**
     * 从方法参数中推断事件类型
     *
     * @param method 监听器方法
     * @return 事件类型
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends GoyaEvent> resolveEventTypeFromMethod(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException(
                String.format("Event listener method %s.%s must have exactly one parameter", 
                    method.getDeclaringClass().getSimpleName(), method.getName()));
        }
        
        Class<?> parameterType = parameterTypes[0];
        
        if (!GoyaEvent.class.isAssignableFrom(parameterType)) {
            throw new IllegalArgumentException(
                String.format("Event listener method %s.%s parameter must extend GoyaEvent", 
                    method.getDeclaringClass().getSimpleName(), method.getName()));
        }
        
        return (Class<? extends GoyaEvent>) parameterType;
    }

    /**
     * 从GoyaEventListener接口的泛型参数中推断事件类型
     *
     * @param listener 事件监听器实例
     * @return 事件类型
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends GoyaEvent> resolveEventTypeFromInterface(GoyaEventListener<?> listener) {
        Class<?> listenerClass = listener.getClass();
        
        // 通过ResolvableType解析泛型
        ResolvableType resolvableType = ResolvableType.forClass(listenerClass)
                .as(GoyaEventListener.class);
        
        if (resolvableType.hasGenerics()) {
            ResolvableType generic = resolvableType.getGeneric(0);
            if (generic.resolve() != null) {
                Class<?> eventType = generic.resolve();
                if (GoyaEvent.class.isAssignableFrom(eventType)) {
                    return (Class<? extends GoyaEvent>) eventType;
                }
            }
        }
        
        // 回退方案：通过接口查找
        Type[] genericInterfaces = listenerClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                Type rawType = parameterizedType.getRawType();
                
                if (rawType.equals(GoyaEventListener.class)) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    if (typeArguments.length > 0) {
                        Type eventType = typeArguments[0];
                        if (eventType instanceof Class) {
                            Class<?> eventClass = (Class<?>) eventType;
                            if (GoyaEvent.class.isAssignableFrom(eventClass)) {
                                return (Class<? extends GoyaEvent>) eventClass;
                            }
                        }
                    }
                }
            }
        }
        
        // 检查父类
        Class<?> superclass = listenerClass.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            try {
                // 创建一个临时的监听器实例来递归解析
                GoyaEventListener<?> tempListener = (GoyaEventListener<?>) listener;
                return resolveEventTypeFromSuperclass(superclass);
            } catch (Exception e) {
                // 忽略异常，继续其他方式
            }
        }
        
        throw new IllegalArgumentException(
            String.format("Cannot resolve event type from listener class: %s. " +
                "Please ensure the class properly implements GoyaEventListener<T> with a concrete type parameter.",
                listenerClass.getName()));
    }

    /**
     * 从父类中解析事件类型
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends GoyaEvent> resolveEventTypeFromSuperclass(Class<?> superclass) {
        ResolvableType resolvableType = ResolvableType.forClass(superclass)
                .as(GoyaEventListener.class);
        
        if (resolvableType.hasGenerics()) {
            ResolvableType generic = resolvableType.getGeneric(0);
            if (generic.resolve() != null) {
                Class<?> eventType = generic.resolve();
                if (GoyaEvent.class.isAssignableFrom(eventType)) {
                    return (Class<? extends GoyaEvent>) eventType;
                }
            }
        }
        
        throw new IllegalArgumentException("Cannot resolve event type from superclass: " + superclass.getName());
    }

    /**
     * 检查类是否实现了GoyaEventListener接口
     *
     * @param clazz 要检查的类
     * @return 是否实现了接口
     */
    public static boolean isGoyaEventListener(Class<?> clazz) {
        return GoyaEventListener.class.isAssignableFrom(clazz);
    }

    /**
     * 获取类的用户定义类（处理代理类）
     *
     * @param bean bean实例
     * @return 用户定义的类
     */
    public static Class<?> getUserClass(Object bean) {
        return ClassUtils.getUserClass(bean);
    }
} 