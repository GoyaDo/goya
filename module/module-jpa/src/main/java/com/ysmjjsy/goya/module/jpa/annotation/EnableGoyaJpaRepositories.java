package com.ysmjjsy.goya.module.jpa.annotation;

import com.ysmjjsy.goya.module.jpa.adapter.JpaRepositoryAdapter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/14 17:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableJpaRepositories(repositoryBaseClass = JpaRepositoryAdapter.class)
public @interface EnableGoyaJpaRepositories {

    String[] basePackages() default {};
}
