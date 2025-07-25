package com.ysmjjsy.goya.security.authentication.client.domain.repository;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientDevice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>Description: OAuth2DeviceRepository </p>
 *
 * @author goya
 * @since 2023/5/15 16:14
 */
public interface SecurityClientDeviceRepository extends BaseRepository<SecurityClientDevice, String> {

    /**
     * 根据 Client ID 查询 OAuth2Device
     *
     * @param clientId OAuth2Device 中的 clientId
     * @return {@link SecurityClientDevice}
     */
    SecurityClientDevice findByClientId(String clientId);

    /**
     * 激活设备
     * <p>
     * 更新设备是否激活的状态
     * <p>
     * 1.@Query注解来将自定义sql语句绑定到自定义方法上。
     * 2.@Modifying注解来标注只需要绑定参数的自定义的更新类语句（更新、插入、删除）。
     * 3.@Modifying只与@Query联合使用，派生类的查询方法和自定义的方法不需要此注解。
     * 4.@Modifying注解时，JPA会以更新类语句来执行，而不再是以查询语句执行。
     *
     * @param clientId    可区分客户端身份的ID
     * @param isActivated 是否已激活
     * @return 影响数据条目
     */
    @Transactional
    @Modifying
    @Query("update SecurityClientDevice d set d.activated = ?2 where d.clientId = ?1")
    int activate(String clientId, boolean isActivated);
}
