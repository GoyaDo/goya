package com.ysmjjsy.goya.module.ip2region.definition;

import com.ysmjjsy.goya.module.ip2region.domain.IpLocation;
import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Function;

/**
 * <p>Description: IP 定位离线搜索定义 </p>
 *
 * @author goya
 * @since 2023/10/23 12:40
 */
public interface Ip2RegionSearcher {

    default String read(IpLocation ip, Function<IpLocation, String> function) {
        if (ObjectUtils.isNotEmpty(ip)) {
            return function.apply(ip);
        } else {
            return null;
        }
    }


    /**
     * ip 位置查询
     *
     * @param ip ip
     * @return Ip 信息详情 {@link IpLocation}
     */
    IpLocation memorySearch(long ip);

    /**
     * ip 位置查询
     *
     * @param ip ip
     * @return Ip 信息详情 {@link IpLocation}
     */
    IpLocation memorySearch(String ip);

    /**
     * 搜索并读取IP信息
     *
     * @param ip       ip
     * @param function {@link Function}
     * @return 地址
     */
    default String get(long ip, Function<IpLocation, String> function) {
        return read(memorySearch(ip), function);
    }

    /**
     * 搜索并读取IP信息
     *
     * @param ip       ip
     * @param function {@link Function}
     * @return 地址
     */
    default String get(String ip, Function<IpLocation, String> function) {
        return read(memorySearch(ip), function);
    }

    /**
     * 获取地址信息
     *
     * @param ip ip
     * @return 地址
     */
    default String getLocation(long ip) {
        return get(ip, IpLocation::getLocation);
    }

    /**
     * 获取地址信息
     *
     * @param ip ip
     * @return 地址
     */
    default String getLocation(String ip) {
        return get(ip, IpLocation::getLocation);
    }

    /**
     * 获取包含 isp 的地址信息
     *
     * @param ip ip
     * @return 地址信息
     */
    default String getLocationWithIsp(long ip) {
        return get(ip, IpLocation::getLocationWithIsp);
    }

    /**
     * 获取包含 isp 的地址信息
     *
     * @param ip ip
     * @return 地址信息
     */
    default String getLocationWithIsp(String ip) {
        return get(ip, IpLocation::getLocationWithIsp);
    }
}
