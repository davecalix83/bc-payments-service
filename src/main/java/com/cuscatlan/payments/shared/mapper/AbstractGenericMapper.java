package com.cuscatlan.payments.shared.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hguzman
 * @param <E>
 * @param <D>
 */
public abstract class AbstractGenericMapper<E, D> implements GenericMapper<E, D> {

    @Autowired
    private ModelMapper modelMapper;

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    public AbstractGenericMapper(
            Class<E> entityClass,
            Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public D toDto(E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public E toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    public List<D> toDtoList(List<E> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
