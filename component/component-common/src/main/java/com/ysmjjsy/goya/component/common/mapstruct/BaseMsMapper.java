package com.ysmjjsy.goya.component.common.mapstruct;

import com.ysmjjsy.goya.component.common.pojo.domain.DTO;
import com.ysmjjsy.goya.component.common.pojo.domain.Entity;
import org.mapstruct.BeanMapping;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * <p>通用双向映射 Mapper 接口</p>
 *
 * @author goya
 * @since 2025/7/25 00:09
 */
@MapperConfig
public interface BaseMsMapper<E extends Entity, D extends DTO> {

    /**
     * Entity -> DTO
     */
    D toDto(E entity);

    /**
     * DTO -> Entity
     */
    E toEntity(D dto);

    /**
     * 批量 Entity -> DTO
     */
    List<D> toDtoList(List<E> entityList);

    /**
     * 批量 DTO -> Entity
     */
    List<E> toEntityList(List<D> dtoList);

    /**
     * DTO -> Entity 增量更新（忽略 null）
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(D dto, @MappingTarget E entity);
}
