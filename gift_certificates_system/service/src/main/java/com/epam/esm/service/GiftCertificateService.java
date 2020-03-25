package com.epam.esm.service;


import com.epam.esm.dto.impl.GiftCertificateDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Interface for certificate service
 */
public interface GiftCertificateService {
    /**
     * Method for saving certificate
     *
     * @param giftCertificateDto certificate DTO object to save
     */
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);

    /**
     * Method for deleting certificate by id
     *
     * @param id positive id of deleting certificate
     */
    GiftCertificateDto delete(long id);

    GiftCertificateDto patch(GiftCertificateDto giftCertificateDto, long id);

    /**
     * Method for getting certificate by params
     *
     * @param params different params for searching
     * @return list of DTOs of certificates according to params
     */
    List<GiftCertificateDto> getByParams(Map<String, String> params);

    /**
     * Method for getting certificate by id
     *
     * @param id positive id of desired certificate
     * @return DTO of desired certificate
     */
    GiftCertificateDto getById(long id);

    /**
     * @param giftCertificateDto certificate
     * @param id                 id
     * @return putted certificate
     */
    GiftCertificateDto put(GiftCertificateDto giftCertificateDto, long id);

    /**
     * @param id    id
     * @param price new price
     * @return certificate with changed price
     */
    GiftCertificateDto changePrice(long id, BigDecimal price);

    long getLastPage(Map<String, String> params);
}
