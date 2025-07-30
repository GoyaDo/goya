package com.ysmjjsy.goya.module.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;
import com.ysmjjsy.goya.component.pojo.constants.DefaultConstants;
import com.ysmjjsy.goya.module.identity.context.GoyaLoginInfoContext;
import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;
import com.ysmjjsy.goya.module.mybatisplus.domain.BaseMpAggregate;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 17:03
 */
public class InjectionMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入填充方法，用于在插入数据时自动填充实体对象中的创建时间、更新时间、创建人、更新人等信息
     *
     * @param metaObject 元对象，用于获取原始对象并进行填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseMpAggregate baseEntity) {
                // 获取当前时间作为创建时间和更新时间，如果创建时间不为空，则使用创建时间，否则使用当前时间
                LocalDateTime current = Objects.nonNull(baseEntity.getCreatedTime()) ? baseEntity.getCreatedTime() : LocalDateTime.now();
                baseEntity.setCreatedTime(current);
                baseEntity.setUpdatedTime(current);

                // 如果创建人为空，则填充当前登录用户的信息
                if (Objects.isNull(baseEntity.getCreateBy())) {
                    GoyaUserPrincipal loginUser = getLoginUser();
                    if (Objects.nonNull(loginUser)) {
                        String userId = loginUser.getId();
                        // 填充创建人、更新人
                        baseEntity.setCreateBy(userId);
                        baseEntity.setUpdateBy(userId);
                    } else {
                        // 填充创建人、更新人
                        baseEntity.setCreateBy(DefaultConstants.DEFAULT_USER_ID);
                        baseEntity.setUpdateBy(DefaultConstants.DEFAULT_USER_ID);
                    }
                }
            } else {
                Date date = new Date();
                this.strictInsertFill(metaObject, "createdTime", Date.class, date);
                this.strictInsertFill(metaObject, "updatedTime", Date.class, date);
            }
        } catch (Exception e) {
            throw new GoyaRuntimeException("自动注入异常 => " + e.getMessage());
        }
    }

    /**
     * 更新填充方法，用于在更新数据时自动填充实体对象中的更新时间和更新人信息
     *
     * @param metaObject 元对象，用于获取原始对象并进行填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseMpAggregate baseEntity) {
                // 获取当前时间作为更新时间，无论原始对象中的更新时间是否为空都填充
                LocalDateTime current = LocalDateTime.now();
                baseEntity.setUpdatedTime(current);

                // 获取当前登录用户的ID，并填充更新人信息
                GoyaUserPrincipal loginUser = getLoginUser();
                if (Objects.nonNull(loginUser)) {
                    baseEntity.setUpdateBy(loginUser.getId());
                } else {
                    baseEntity.setUpdateBy(DefaultConstants.DEFAULT_USER_ID);
                }
            } else {
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            }
        } catch (Exception e) {
            throw new GoyaRuntimeException("自动注入异常 => " + e.getMessage());
        }
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    private GoyaUserPrincipal getLoginUser() {
        GoyaUserPrincipal loginUser;
        try {
            loginUser = GoyaLoginInfoContext.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        return loginUser;
    }
}
