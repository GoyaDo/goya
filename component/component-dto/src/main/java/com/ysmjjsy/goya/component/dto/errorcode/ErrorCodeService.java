package com.ysmjjsy.goya.component.dto.errorcode;

import com.ysmjjsy.goya.component.dto.enums.GoyaEnum;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 13:30
 */
public interface ErrorCodeService extends GoyaEnum<String> {

    @Override
    default String getValue() {
        return getCode();
    }

    String getCode();

    int getStatus();

}
