package com.ysmjjsy.goya.component.event.core;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import com.ysmjjsy.goya.component.event.annotation.GoyaAnnoEventListener;
import com.ysmjjsy.goya.component.event.configuration.EventBusProperties;
import com.ysmjjsy.goya.component.event.processor.EventTypeResolver;
import com.ysmjjsy.goya.component.event.processor.MethodGoyaEventListenerAdapter;
import com.ysmjjsy.goya.component.event.transport.EventTransport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/20 21:08
 */
public class EventListenerAutoRegistrar implements SmartInitializingSingleton, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(EventListenerAutoRegistrar.class);

    private ApplicationContext applicationContext;
    private final AtomicInteger registeredCount = new AtomicInteger(0);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        logger.info("=== 开始事件监听器自动注册 ===");

        try {
            // 获取必要的组件
            GoyaEventBus eventBus = getEventBus();
            EventRouter eventRouter = getEventRouter();
            List<EventTransport> eventTransports = getEventTransports();

            if (eventBus == null) {
                logger.warn("EventBus未找到，跳过事件监听器注册");
                return;
            }

            logger.info("发现 {} 个事件传输组件: {}", eventTransports.size(),
                    eventTransports.stream().map(EventTransport::getTransportType).toList());

            // 注册监听器
            int interfaceListeners = registerInterfaceListeners(eventBus, eventRouter, eventTransports);
            int annotationListeners = registerAnnotationListeners(eventBus, eventRouter, eventTransports);

            int totalRegistered = interfaceListeners + annotationListeners;
            registeredCount.set(totalRegistered);

            logger.info("=== 事件监听器自动注册完成 ===");
            logger.info("接口监听器: {}, 注解监听器: {}, 总计: {}",
                    interfaceListeners, annotationListeners, totalRegistered);

        } catch (Exception e) {
            logger.error("事件监听器自动注册失败", e);
        }
    }

    private GoyaEventBus getEventBus() {
        try {
            return applicationContext.getBean(GoyaEventBus.class);
        } catch (Exception e) {
            logger.debug("EventBus不可用: {}", e.getMessage());
            return null;
        }
    }

    private EventRouter getEventRouter() {
        try {
            return applicationContext.getBean(EventRouter.class);
        } catch (Exception e) {
            logger.debug("EventRouter不可用: {}", e.getMessage());
            return null;
        }
    }

    private List<EventTransport> getEventTransports() {
        try {
            Map<String, EventTransport> transportBeans = applicationContext.getBeansOfType(EventTransport.class);
            return new ArrayList<>(transportBeans.values());
        } catch (Exception e) {
            logger.debug("EventTransports不可用: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 注册实现 GoyaEventListener 接口的监听器
     */
    private int registerInterfaceListeners(GoyaEventBus eventBus, EventRouter eventRouter, List<EventTransport> eventTransports) {
        Map<String, GoyaEventListener> listeners = applicationContext.getBeansOfType(GoyaEventListener.class);

        logger.info("发现 {} 个 GoyaEventListener 接口实现", listeners.size());

        int registered = 0;
        for (Map.Entry<String, GoyaEventListener> entry : listeners.entrySet()) {
            String beanName = entry.getKey();
            GoyaEventListener<?> listener = entry.getValue();

            try {
                // 推断事件类型
                Class<? extends GoyaEvent> eventType = EventTypeResolver.resolveEventTypeFromInterface(listener);

                // 注册到本地事件总线（接口监听器没有注解，使用默认配置）
                registerToEventBus(eventBus, listener, eventType, beanName, null);

                // 注册到所有传输层
                registerToTransports(eventRouter, eventTransports, listener, eventType, beanName);

                registered++;
                logger.info("成功注册接口监听器: {} -> {}", beanName, eventType.getSimpleName());

            } catch (Exception e) {
                logger.error("注册接口监听器失败: {}", beanName, e);
            }
        }

        return registered;
    }

    /**
     * 注册标注 @GoyaAnnoEventListener 注解的监听器方法
     */
    private int registerAnnotationListeners(GoyaEventBus eventBus, EventRouter eventRouter, List<EventTransport> eventTransports) {
        Map<String, Object> allBeans = applicationContext.getBeansOfType(Object.class);

        int registered = 0;
        for (Map.Entry<String, Object> entry : allBeans.entrySet()) {
            String beanName = entry.getKey();
            Object bean = entry.getValue();

            try {
                int methodListeners = registerAnnotationListenersFromBean(eventBus, eventRouter, eventTransports, beanName, bean);
                registered += methodListeners;

                if (methodListeners > 0) {
                    logger.info("从Bean {} 注册了 {} 个注解监听器", beanName, methodListeners);
                }
            } catch (Exception e) {
                logger.error("从Bean {} 注册注解监听器失败", beanName, e);
            }
        }

        return registered;
    }

    private int registerAnnotationListenersFromBean(GoyaEventBus eventBus, EventRouter eventRouter,
                                                    List<EventTransport> eventTransports, String beanName, Object bean) {
        Class<?> targetClass = EventTypeResolver.getUserClass(bean);
        List<Method> eventListenerMethods = findEventListenerMethods(targetClass);

        if (eventListenerMethods.isEmpty()) {
            return 0;
        }

        int registered = 0;
        for (Method method : eventListenerMethods) {
            try {
                MethodGoyaEventListenerAdapter adapter = createMethodAdapter(bean, method);
                Class<? extends GoyaEvent> eventType = EventTypeResolver.resolveEventTypeFromMethod(method);

                // 获取注解信息
                GoyaAnnoEventListener annotation = AnnotatedElementUtils.findMergedAnnotation(method, GoyaAnnoEventListener.class);
                
                // 注册到本地事件总线
                registerToEventBus(eventBus, adapter, eventType, beanName + "." + method.getName(), annotation);

                // 注册到所有传输层
                registerToTransports(eventRouter, eventTransports, adapter, eventType, beanName + "." + method.getName());

                registered++;
                logger.info("成功注册注解监听器: {}.{} -> {}",
                        targetClass.getSimpleName(), method.getName(), eventType.getSimpleName());

            } catch (Exception e) {
                logger.error("注册注解监听器失败: {}.{}",
                        targetClass.getSimpleName(), method.getName(), e);
            }
        }

        return registered;
    }

    private List<Method> findEventListenerMethods(Class<?> targetClass) {
        List<Method> methods = new ArrayList<>();

        ReflectionUtils.doWithMethods(targetClass, method -> {
            GoyaAnnoEventListener annotation = AnnotatedElementUtils.findMergedAnnotation(method, GoyaAnnoEventListener.class);
            if (annotation != null) {
                methods.add(method);
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);

        return methods;
    }

    private MethodGoyaEventListenerAdapter createMethodAdapter(Object bean, Method method) {
        GoyaAnnoEventListener annotation = AnnotatedElementUtils.findMergedAnnotation(method, GoyaAnnoEventListener.class);

        if (annotation == null) {
            throw new IllegalStateException("Method should have @GoyaAnnoEventListener annotation");
        }

        return new MethodGoyaEventListenerAdapter(bean, method, annotation);
    }

    /**
     * 注册监听器到事件总线
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerToEventBus(GoyaEventBus eventBus, GoyaEventListener<?> listener,
                                    Class<? extends GoyaEvent> eventType, String listenerName,
                                    GoyaAnnoEventListener annotation) {
        
        // 解析topics配置
        Set<String> topics = new HashSet<>();
        if (annotation != null && annotation.topics().length > 0) {
            topics.addAll(Arrays.asList(annotation.topics()));
        }
        
        // 创建LocalEventListenerWrapper
        LocalEventListenerWrapper wrapper = new LocalEventListenerWrapper(
            listener, 
            eventType, 
            topics,
            annotation != null ? annotation.condition() : null,
            annotation != null ? annotation.priority() : 0,
            annotation != null ? annotation.async() : false,
            listenerName
        );

        eventBus.subscribe(wrapper, (Class) eventType);
        logger.debug("注册到EventBus: {} -> {} (topics: {})",
                listenerName, eventType.getSimpleName(), topics.isEmpty() ? "all" : topics);
    }

    /**
     * 注册监听器到所有传输层
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerToTransports(EventRouter eventRouter, List<EventTransport> eventTransports,
                                      GoyaEventListener<?> listener, Class<? extends GoyaEvent> eventType, String listenerName) {
        if (eventTransports.isEmpty()) {
            return;
        }

        // 修复：根据事件类型确定主题，而不是传递给router
        String topic = getTopicForEventType(eventRouter, eventType);

        // 注册到所有传输层
        for (EventTransport transport : eventTransports) {
            try {
                transport.subscribe(topic, (GoyaEventListener) listener, (Class) eventType);
                logger.debug("📡 注册到{}: {} -> {} (topic: {})",
                        transport.getTransportType(), listenerName, eventType.getSimpleName(), topic);
            } catch (Exception e) {
                logger.error("❌ 注册到{}失败: {}", transport.getTransportType(), listenerName, e);
            }
        }
    }

    /**
     * 新增：根据事件类型确定主题
     */
    private String getTopicForEventType(EventRouter eventRouter, Class<? extends GoyaEvent> eventType) {
        String applicationName = GoyaContextHolder.getInstance().getApplicationName();
        if (StringUtils.isBlank(applicationName)) {
            EventBusProperties bean = applicationContext.getBean(EventBusProperties.class);
            return bean.getDefaultTopic();
        }else{
            return applicationName;
        }
    }


    public int getRegisteredListenerCount() {
        return registeredCount.get();
    }
}