package com.cuscatlan.payments.shared.mapper;

public interface GenericMapper<E, D> {

    D toDto(E entidad);

    E toEntity(D dto);
}
