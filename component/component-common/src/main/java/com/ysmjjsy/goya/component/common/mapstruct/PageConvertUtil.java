package com.ysmjjsy.goya.component.common.mapstruct;

import com.ysmjjsy.goya.component.dto.domain.DTO;
import com.ysmjjsy.goya.component.dto.domain.Entity;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/25 00:11
 */
@UtilityClass
public class PageConvertUtil {

    /**
     * <p>分页对象转换</p>
     *
     * @param entityPage 分页实体
     * @param mapper     Mapper
     * @param <E>        实体
     * @param <D>        DTO
     * @return 分页DTO
     */
    public static <E extends Entity, D extends DTO> Page<D> convert(Page<E> entityPage, BaseMsMapper<E, D> mapper) {
        return entityPage.map(mapper::toDto);
    }
}
