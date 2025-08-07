package com.ysmjjsy.goya.module.mybatisplus.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.PostInitTableInfoHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import com.ysmjjsy.goya.component.common.resolver.YmlPropertySourceFactory;
import com.ysmjjsy.goya.component.db.adapter.BaseRepositoryAdapter;
import com.ysmjjsy.goya.component.db.constants.DbConstants;
import com.ysmjjsy.goya.module.mybatisplus.adapter.MybatisPlusRepositoryAdapter;
import com.ysmjjsy.goya.module.mybatisplus.enhance.SnowIdentifierGenerator;
import com.ysmjjsy.goya.module.mybatisplus.handler.InjectionMetaObjectHandler;
import com.ysmjjsy.goya.module.mybatisplus.handler.MybatisExceptionHandler;
import com.ysmjjsy.goya.module.mybatisplus.handler.PlusPostInitTableInfoHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/22 23:42
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource(value = "classpath:common-mybatis.yml", factory = YmlPropertySourceFactory.class)
@MapperScan(
        value = "${mybatis-plus.mapperPackage}",
        markerInterface = MybatisPlusRepositoryAdapter.class
)
public class MybatisPlusAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [mybatis-plus] MybatisPlusAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnProperty(name = DbConstants.PROPERTY_PREFIX_DB + ".mode", havingValue = "mybatis_plus")
    @Primary
    public BaseRepositoryAdapter mybatisAdapter(BaseMapper baseMapper) {
        return new MybatisPlusRepositoryAdapter<>(baseMapper);
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户插件 必须放到第一位
        try {
            TenantLineInnerInterceptor tenant = ApplicationContextHolder.getBean(TenantLineInnerInterceptor.class);
            interceptor.addInnerInterceptor(tenant);
        } catch (BeansException ignore) {
        }
        // 数据权限处理
//        interceptor.addInnerInterceptor(dataPermissionInterceptor());
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        return interceptor;
    }

//    /**
//     * 数据权限拦截器
//     */
//    public PlusDataPermissionInterceptor dataPermissionInterceptor() {
//        return new PlusDataPermissionInterceptor(SpringUtils.getProperty("mybatis-plus.mapperPackage"));
//    }

    /**
     * 数据权限切面处理器
     */
//    @Bean
//    public DataPermissionAspect dataPermissionAspect() {
//        return new DataPermissionAspect();
//    }

    /**
     * 分页插件，自动识别数据库类型
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 分页合理化
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 元对象字段填充控制器
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        InjectionMetaObjectHandler injectionMetaObjectHandler = new InjectionMetaObjectHandler();
        log.trace("[Goya] |- module [mybatis-plus] |- bean [metaObjectHandler] register.");
        return injectionMetaObjectHandler;
    }

    /**
     * 使用网卡信息绑定雪花生成器
     * 防止集群雪花ID重复
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        SnowIdentifierGenerator snowIdentifierGenerator = new SnowIdentifierGenerator();
        log.trace("[Goya] |- module [mybatis-plus] |- bean [idGenerator] register.");
        return snowIdentifierGenerator;
    }

    /**
     * 异常处理器
     */
    @Bean
    public MybatisExceptionHandler mybatisExceptionHandler() {
        MybatisExceptionHandler mybatisExceptionHandler = new MybatisExceptionHandler();
        log.trace("[Goya] |- module [mybatis-plus] |- bean [mybatisExceptionHandler] register.");
        return mybatisExceptionHandler;
    }

    /**
     * 初始化表对象处理器
     */
    @Bean
    public PostInitTableInfoHandler postInitTableInfoHandler() {
        PlusPostInitTableInfoHandler plusPostInitTableInfoHandler = new PlusPostInitTableInfoHandler();
        log.trace("[Goya] |- module [mybatis-plus] |- bean [postInitTableInfoHandler] register.");
        return plusPostInitTableInfoHandler;
    }

    /**
     * PaginationInnerInterceptor 分页插件，自动识别数据库类型
     * https://baomidou.com/pages/97710a/
     * OptimisticLockerInnerInterceptor 乐观锁插件
     * https://baomidou.com/pages/0d93c0/
     * MetaObjectHandler 元对象字段填充控制器
     * https://baomidou.com/pages/4c6bcf/
     * ISqlInjector sql注入器
     * https://baomidou.com/pages/42ea4a/
     * BlockAttackInnerInterceptor 如果是对全表的删除或更新操作，就会终止该操作
     * https://baomidou.com/pages/f9a237/
     * IllegalSQLInnerInterceptor sql性能规范插件(垃圾SQL拦截)
     * IdentifierGenerator 自定义主键策略
     * https://baomidou.com/pages/568eb2/
     * TenantLineInnerInterceptor 多租户插件
     * https://baomidou.com/pages/aef2f2/
     * DynamicTableNameInnerInterceptor 动态表名插件
     * https://baomidou.com/pages/2a45ff/
     */

}
