package com.epam.esm.dto;

/**
 * Interface for transfering data of certificates
 */
public interface DtoModelConverter<T, V> {
    /**
     * Method for converting  to dto
     *
     * @param v entity to convert
     */
    T toDto(V v);

    /**
     * Method for converting  to model
     *
     * @param t entity to convert
     */
    V toModel(T t);
}
