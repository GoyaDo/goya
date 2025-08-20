package com.ysmjjsy.goya.component.distributedid.core.snowflake;

import org.springframework.beans.factory.InitializingBean;

/**
 * <p>使用随机数获取雪花 WorkId</p>
 *
 * @author goya
 * @since 2025/7/25 00:34
 */
public class RandomWorkIdChoose extends AbstractWorkIdChooseTemplate implements InitializingBean {

    @Override
    public WorkIdWrapper chooseWorkId() {
        int start = 0;
        int end = 31;
        return new WorkIdWrapper(getRandom(start, end), getRandom(start, end));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chooseAndInit();
    }

    private static long getRandom(int start, int end) {
        return (long) (Math.random() * (end - start + 1) + start);
    }
}
