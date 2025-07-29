package com.ysmjjsy.goya.module.ip2region.domain;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.pojo.constants.SymbolConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Description: Ip 信息详情 </p>
 *
 * @author goya
 * @since 2023/10/22 23:00
 */
@Setter
@Getter
public class IpLocation implements Serializable {

    @Serial
    private static final long serialVersionUID = 5770180742382945165L;
    /**
     * 国家
     */
    private String country;
    /**
     * 区域
     */
    private String region;
    /**
     * 省
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 运营商
     */
    private String isp;

    public String getLocation() {
        return createLocation(false, SymbolConstants.BLANK);
    }

    public String getLocationWithIsp() {
        return createLocation(true, SymbolConstants.SPACE);
    }

    private String createLocation(boolean withIsp, String separator) {
        Set<String> result = new LinkedHashSet<>();
        result.add(country);
        result.add(region);
        result.add(province);
        result.add(city);
        if (withIsp) {
            result.add(isp);
        }
        result.removeIf(Objects::isNull);
        return StringUtils.join(result, separator);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("country", country)
                .add("region", region)
                .add("province", province)
                .add("city", city)
                .add("isp", isp)
                .toString();
    }
}
