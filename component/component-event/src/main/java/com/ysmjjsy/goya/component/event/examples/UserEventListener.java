package com.ysmjjsy.goya.component.event.examples;

import com.ysmjjsy.goya.component.event.annotation.GoyaAnnoEventListener;
import com.ysmjjsy.goya.component.event.core.EventHandleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户事件监听器示例
 */
@Component
public class UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    /**
     * 本地处理用户创建事件
     */
    @GoyaAnnoEventListener(async = true, priority = 1)
    public void handleUserCreatedLocal(UserCreatedEvent event) throws EventHandleException {
        try {
            logger.info("本地处理用户创建事件: {}", event);
            
            // 执行本地业务逻辑
            sendWelcomeEmail(event.getEmail());
            updateUserStatistics();
            
            logger.info("用户创建事件本地处理完成: userId={}", event.getUserId());
            
        } catch (Exception e) {
            throw new EventHandleException(event.getEventId(), "Failed to handle user created event locally", e);
        }
    }

    /**
     * 远程处理用户创建事件
     */
    @GoyaAnnoEventListener(
        topics = "user-events", 
        async = true,
        condition = "#event.headers['priority'] == 'high'"
    )
    public void handleUserCreatedRemote(UserCreatedEvent event) throws EventHandleException {
        try {
            logger.info("远程处理用户创建事件: {}", event);
            
            // 执行远程业务逻辑
            notifyExternalSystems(event);
            syncToDataWarehouse(event);
            
            logger.info("用户创建事件远程处理完成: userId={}", event.getUserId());
            
        } catch (Exception e) {
            throw new EventHandleException(event.getEventId(), "Failed to handle user created event remotely", e);
        }
    }

    /**
     * 事务性处理用户创建事件
     */
    @GoyaAnnoEventListener(
        transactional = true,
        phase = GoyaAnnoEventListener.TransactionPhase.AFTER_COMMIT
    )
    public void handleUserCreatedAfterCommit(UserCreatedEvent event) throws EventHandleException {
        try {
            logger.info("事务提交后处理用户创建事件: {}", event);
            
            // 在事务提交后执行的业务逻辑
            sendNotificationToAdmins(event);
            triggerUserOnboarding(event);
            
            logger.info("用户创建事件事务后处理完成: userId={}", event.getUserId());
            
        } catch (Exception e) {
            throw new EventHandleException(event.getEventId(), "Failed to handle user created event after commit", e);
        }
    }

    private void sendWelcomeEmail(String email) {
        // 模拟发送欢迎邮件
        logger.debug("发送欢迎邮件到: {}", email);
        try {
            Thread.sleep(100); // 模拟IO操作
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateUserStatistics() {
        // 模拟更新用户统计
        logger.debug("更新用户统计信息");
    }

    private void notifyExternalSystems(UserCreatedEvent event) {
        // 模拟通知外部系统
        logger.debug("通知外部系统用户创建: userId={}", event.getUserId());
    }

    private void syncToDataWarehouse(UserCreatedEvent event) {
        // 模拟同步到数据仓库
        logger.debug("同步用户数据到数据仓库: userId={}", event.getUserId());
    }

    private void sendNotificationToAdmins(UserCreatedEvent event) {
        // 模拟发送通知给管理员
        logger.debug("通知管理员新用户注册: userId={}", event.getUserId());
    }

    private void triggerUserOnboarding(UserCreatedEvent event) {
        // 模拟触发用户引导流程
        logger.debug("触发用户引导流程: userId={}", event.getUserId());
    }
}