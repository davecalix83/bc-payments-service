package com.cuscatlan.payments.shared.mapper;

/**
 * Generic interface for mapping between entity and Data Transfer Object (DTO).
 *
 * @param <E> the entity type
 * @param <D> the DTO type
 */
public interface GenericMapper<E, D> {

    /**
     * Maps an entity to its corresponding DTO.
     *
     * @param entity the entity to be mapped
     * @return the mapped DTO
     */
    D toDto(E entity);

    /**
     * Maps a DTO to its corresponding entity.
     *
     * @param dto the DTO to be mapped
     * @return the mapped entity
     */
    E toEntity(D dto);
}
