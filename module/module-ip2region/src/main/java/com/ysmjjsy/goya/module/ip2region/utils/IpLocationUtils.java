package com.ysmjjsy.goya.module.ip2region.utils;

import com.ysmjjsy.goya.component.common.constants.SymbolConstants;
import com.ysmjjsy.goya.module.ip2region.domain.IpLocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * <p>Description: Ip 信息解析工具类 </p>
 *
 * @author goya
 * @since 2023/10/23 12:02
 */
public class IpLocationUtils {

    private static String check(String element) {
        if (StringUtils.isBlank(element) || StringUtils.equals(element, SymbolConstants.ZERO)) {
            return null;
        } else {
            return element;
        }
    }

    /**
     * 区域信息转成 Ip 位置信息实体
     *
     * @param region    区域
     * @param separator 分隔符
     * @param cover     补足位数
     * @param isp       运营商
     * @return Ip 信息详情 {@link IpLocation}
     */
    private static IpLocation toIpLocation(String region, String separator, int cover, String isp) {
        Assert.notNull(region, "The region is required.");

        String[] informations = StringUtils.split(region, separator);
        if (informations.length < cover) {
            informations = Arrays.copyOf(informations, cover);
        }

        IpLocation location = new IpLocation();
        location.setCountry(check(informations[0]));
        location.setProvince(check(informations[1]));
        location.setCity(check(informations[2]));
        location.setRegion(check(informations[3]));
        location.setIsp(check(isp));
        return location;
    }

    /**
     * 将 DataBlock 数据转换成 IpInformation 实体
     *
     * @param region 区域数据
     * @return Ip 信息详情 {@link IpLocation}
     */
    public static IpLocation toIpV4Location(String region) {
        return toIpLocation(region, SymbolConstants.PIPE, 5, null);
    }

    /**
     * 将 ipv6 地区转换成 IpInformation 实体
     *
     * @param region 区域数据
     * @param isp    运营商
     * @return Ip 信息详情 {@link IpLocation}
     */
    public static IpLocation toIpV6Location(String region, String isp) {
        return toIpLocation(region, "t", 4, isp);
    }

    /**
     * 将 ipv6 地区转换成 IpInformation 实体
     *
     * @param record 区域数据
     * @return Ip 信息详情 {@link IpLocation}
     */
    public static IpLocation toIpV6Location(String[] record) {
        return toIpV6Location(record[0], record[1]);
    }

    public static String[] getIpV4Part(String ip) {
        return StringUtils.split(ip, SymbolConstants.PERIOD);
    }
}
