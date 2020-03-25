package com.epam.esm.service;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.PurchaseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Class for purchases
 */
public interface PurchaseService {
    /**
     * @param id             id of certificate
     * @param authentication authentification
     * @return saved purchase
     */
    PurchaseDto buy(long id, Authentication authentication);

    /**
     * @param id id of user
     * @return list of users purchases
     */
    List<PurchaseDto> getUserPurchases(long id, String pageSize, String page);

    /**
     * @param id id of user
     * @return list of users certificates
     */
    List<GiftCertificateDto> getUserCertificates(long id, String pageSize, String page);
}
