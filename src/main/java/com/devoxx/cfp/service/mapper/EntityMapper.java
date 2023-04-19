package com.devoxx.cfp.service.mapper;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */
public interface EntityMapper<D, E> {

    D toDto(E entity);
}
