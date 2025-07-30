package com.ysmjjsy.goya.module.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.PostInitTableInfoHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 17:22
 */
public class PlusPostInitTableInfoHandler implements PostInitTableInfoHandler {

    @Override
    public void postTableInfo(TableInfo tableInfo, Configuration configuration) {
        String flag = SpringUtil.getProperty("mybatis-plus.enableLogicDelete", "true");
        // 只有关闭时 统一设置false 为true时mp自动判断不处理
        if (StringUtils.equals(flag, "false")) {
//            ReflectUtils.setFieldValue(tableInfo, "withLogicDelete", false);
        }
    }

}
