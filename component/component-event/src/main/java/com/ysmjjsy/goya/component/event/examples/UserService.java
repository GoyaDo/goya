package com.ysmjjsy.goya.component.event.examples;

import com.ysmjjsy.goya.component.event.core.EventPublishResult;
import com.ysmjjsy.goya.component.event.core.GoyaEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务示例
 * 展示如何使用统一事件总线
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private GoyaEventBus eventBus;

    /**
     * 创建用户（同步发布事件）
     */
    public void createUser(String username, String email) {
        logger.info("开始创建用户: username={}, email={}", username, email);
        
        try {
            // 1. 执行业务逻辑（保存用户到数据库等）
            Long userId = saveUserToDatabase(username, email);
            
            // 2. 发布事件
            UserCreatedEvent event = new UserCreatedEvent(this, userId, username, email);
            EventPublishResult result = eventBus.publish(event);
            
            if (result.isSuccess()) {
                logger.info("用户创建成功，事件已发布: userId={}, eventId={}", userId, event.getEventId());
                logger.info("事件处理结果: 本地监听器={}, 远程目标={}", 
                           result.getLocalListeners(), result.getRemoteTargets());
            } else {
                logger.error("用户创建成功，但事件发布失败: userId={}, errors={}", userId, result.getErrors());
            }
            
        } catch (Exception e) {
            logger.error("用户创建失败: username={}, email={}", username, email, e);
            throw new RuntimeException("Failed to create user", e);
        }
    }

    /**
     * 从特定服务创建用户（测试topic过滤功能）
     */
    public void createUserFromService(String username, String email, String serviceName, boolean isPremium, boolean enableAudit) {
        logger.info("开始从{}创建用户: username={}, email={}, premium={}", serviceName, username, email, isPremium);
        
        try {
            // 1. 执行业务逻辑
            Long userId = saveUserToDatabase(username, email);
            
            // 2. 创建事件，指定特定的topic
            UserCreatedEvent event = new UserCreatedEvent(this, userId, username, email);
            
            // 设置自定义topic
            event.setTopic(serviceName);
            
            // 添加额外的头信息用于条件匹配
            if (isPremium) {
                event.addHeader("userType", "premium");
            }
            if (enableAudit) {
                event.addHeader("audit", "true");
            }
            
            // 3. 发布事件
            EventPublishResult result = eventBus.publish(event);
            
            if (result.isSuccess()) {
                logger.info("用户创建成功 [{}]: userId={}, eventId={}, 本地监听器={}", 
                           serviceName, userId, event.getEventId(), result.getLocalListeners());
            } else {
                logger.error("用户创建成功，但事件发布失败 [{}]: userId={}, errors={}", 
                           serviceName, userId, result.getErrors());
            }
            
        } catch (Exception e) {
            logger.error("用户创建失败 [{}]: username={}, email={}", serviceName, username, email, e);
            throw new RuntimeException("Failed to create user from " + serviceName, e);
        }
    }

    /**
     * 创建用户（异步发布事件）
     */
    public void createUserAsync(String username, String email) {
        logger.info("开始异步创建用户: username={}, email={}", username, email);
        
        try {
            // 1. 执行业务逻辑
            Long userId = saveUserToDatabase(username, email);
            
            // 2. 异步发布事件
            UserCreatedEvent event = new UserCreatedEvent(this, userId, username, email);
            eventBus.publishAsync(event)
                   .whenComplete((result, throwable) -> {
                       if (throwable != null) {
                           logger.error("异步事件发布失败: userId={}, eventId={}", userId, event.getEventId(), throwable);
                       } else if (result.isSuccess()) {
                           logger.info("异步事件发布成功: userId={}, eventId={}", userId, event.getEventId());
                       } else {
                           logger.error("异步事件发布失败: userId={}, errors={}", userId, result.getErrors());
                       }
                   });
            
            logger.info("用户创建完成，异步事件已提交: userId={}", userId);
            
        } catch (Exception e) {
            logger.error("异步用户创建失败: username={}, email={}", username, email, e);
            throw new RuntimeException("Failed to create user async", e);
        }
    }

    /**
     * 创建用户（事务性发布事件）
     */
    @Transactional
    public void createUserTransactional(String username, String email) {
        logger.info("开始事务性创建用户: username={}, email={}", username, email);
        
        try {
            // 1. 执行业务逻辑（在事务中）
            Long userId = saveUserToDatabase(username, email);
            
            // 2. 事务性发布事件（将在事务提交后发布远程事件）
            UserCreatedEvent event = new UserCreatedEvent(this, userId, username, email);
            EventPublishResult result = eventBus.publishTransactional(event);
            
            logger.info("事务性用户创建完成: userId={}, eventId={}", userId, event.getEventId());
            logger.info("本地事件已处理，远程事件将在事务提交后发布");
            
            // 如果这里抛出异常，事务会回滚，远程事件不会发布
            // simulateBusinessException();
            
        } catch (Exception e) {
            logger.error("事务性用户创建失败: username={}, email={}", username, email, e);
            throw new RuntimeException("Failed to create user transactional", e);
        }
    }

    /**
     * 批量创建用户
     */
    public void batchCreateUsers(String[] usernames, String[] emails) {
        if (usernames.length != emails.length) {
            throw new IllegalArgumentException("Usernames and emails arrays must have the same length");
        }
        
        logger.info("开始批量创建用户: count={}", usernames.length);
        
        for (int i = 0; i < usernames.length; i++) {
            try {
                createUserAsync(usernames[i], emails[i]);
            } catch (Exception e) {
                logger.error("批量创建用户失败: index={}, username={}, email={}", i, usernames[i], emails[i], e);
                // 继续处理其他用户
            }
        }
        
        logger.info("批量用户创建任务提交完成");
    }

    private Long saveUserToDatabase(String username, String email) {
        // 模拟保存用户到数据库
        logger.debug("保存用户到数据库: username={}, email={}", username, email);
        
        try {
            Thread.sleep(50); // 模拟数据库操作
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 返回模拟的用户ID
        return System.currentTimeMillis() % 10000;
    }

    @SuppressWarnings("unused")
    private void simulateBusinessException() {
        // 模拟业务异常
        throw new RuntimeException("Simulated business exception");
    }
}