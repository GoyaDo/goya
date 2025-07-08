package com.ysmjjsy.goya.component.event.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ysmjjsy.goya.component.event.core.EventRoutingStrategy;
import com.ysmjjsy.goya.component.event.core.GoyaEvent;
import lombok.Getter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 用户创建事件示例
 */
@Getter
public class UserCreatedEvent extends GoyaEvent {

    @Serial
    private static final long serialVersionUID = 4655583105671366163L;

    // Getters
    @JsonProperty("userId")
    private final Long userId;
    
    @JsonProperty("username")
    private final String username;
    
    @JsonProperty("email")
    private final String email;
    
    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;

    public UserCreatedEvent(Object source, Long userId, String username, String email) {
        super(source);
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        
        // 设置为本地和远程都处理
        setRoutingStrategy(EventRoutingStrategy.LOCAL_AND_REMOTE);
        
        // 添加一些额外的头信息
        addHeader("eventCategory", "user");
        addHeader("priority", "high");
    }

    @Override
    public String toString() {
        return String.format("UserCreatedEvent{userId=%d, username='%s', email='%s', createdAt=%s}", 
                userId, username, email, createdAt);
    }
}