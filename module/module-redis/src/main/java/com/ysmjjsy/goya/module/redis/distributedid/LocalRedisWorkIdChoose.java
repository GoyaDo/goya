package com.ysmjjsy.goya.module.redis.distributedid;

import cn.hutool.v7.core.collection.CollUtil;
import com.ysmjjsy.goya.component.distributedid.core.snowflake.AbstractWorkIdChooseTemplate;
import com.ysmjjsy.goya.component.distributedid.core.snowflake.RandomWorkIdChoose;
import com.ysmjjsy.goya.component.distributedid.core.snowflake.WorkIdWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/25 00:39
 */
@Slf4j
@RequiredArgsConstructor
public class LocalRedisWorkIdChoose extends AbstractWorkIdChooseTemplate implements InitializingBean {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public WorkIdWrapper chooseWorkId() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/chooseWorkIdLua.lua")));
        List<Long> luaResultList = null;
        try {
            redisScript.setResultType(List.class);
            luaResultList = (ArrayList) this.stringRedisTemplate.execute(redisScript, null);
        } catch (Exception ex) {
            log.error("Redis Lua 脚本获取 WorkId 失败", ex);
        }
        return CollUtil.isNotEmpty(luaResultList) ? new WorkIdWrapper(luaResultList.get(0), luaResultList.get(1)) : new RandomWorkIdChoose().chooseWorkId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chooseAndInit();
    }
}
