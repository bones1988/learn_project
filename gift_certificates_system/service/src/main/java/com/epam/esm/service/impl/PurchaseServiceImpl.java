package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.GiftCertificateModelConverter;
import com.epam.esm.dto.converter.PurchaseModelConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.PurchaseDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.ShopUser;
import com.epam.esm.repository.PurchaseRepository;
import com.epam.esm.repository.ShopUserRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @see PurchaseService
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private static final int MAXIMUM_PAGESIZE = 50;
    @Autowired
    private ShopUserRepository shopUserRepository;
    @Autowired
    private GiftCertificateRepositoryImpl giftCertificateRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseModelConverter purchaseModelConverter;
    @Autowired
    private GiftCertificateModelConverter certificateModelConverter;

    /**
     * @see PurchaseService
     */
    @Override
    @Transactional
    public PurchaseDto buy(long id, Authentication authentication) {
        String login = authentication.getName();
        ShopUser shopUser = shopUserRepository.getByLogin(login);
        AbstractFilter filter = new GiftCertificateFilter();
        filter.setId(id);
        GiftCertificate giftCertificate = giftCertificateRepository
                .query(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.valueOf(id), ErrorCode.CERTIFICATE_NOT_FOUND_BY_ID));
        if (!Boolean.TRUE.equals(giftCertificate.isActive())) {
            throw new NotFoundException("", ErrorCode.DELETED_CERTIFICATE);
        }
        Purchase purchase = new Purchase();
        purchase.setPrice(giftCertificate.getPrice());
        purchase.setGiftCertificate(giftCertificate);
        purchase.setUserId(shopUser.getId());
        return purchaseModelConverter.toDto(purchaseRepository.buy(purchase));
    }

    /**
     * @see PurchaseService
     */
    @Override
    public List<PurchaseDto> getUserPurchases(long id, String pageSize, String page) {
        int limit;
        int offset;
        try {
            limit = Integer.parseInt(pageSize);
            int pageNumber = Integer.parseInt(page);
            offset = (limit * (pageNumber - 1));
        } catch (NumberFormatException e) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        if (limit <= 0 || offset < 0||limit>MAXIMUM_PAGESIZE) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        List<PurchaseDto> purchaseDtos = purchaseRepository.getUserPurchases(id, limit, offset)
                .stream()
                .map(purchaseModelConverter::toDto)
                .collect(Collectors.toList());
        if (purchaseDtos.isEmpty()) {
            throw new NotFoundException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        return purchaseDtos;
    }

    /**
     * @see PurchaseService
     */
    @Override
    public List<GiftCertificateDto> getUserCertificates(long id, String pageSize, String page) {
        int limit;
        int offset;
        try {
            limit = Integer.parseInt(pageSize);
            int pageNumber = Integer.parseInt(page);
            offset = (limit * (pageNumber - 1));
        } catch (NumberFormatException e) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        if (limit <= 0 || offset < 0||limit>MAXIMUM_PAGESIZE) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        List<GiftCertificateDto> giftCertificateDtos = purchaseRepository
               .getUserCertificates(id, limit, offset)
                .stream()
                .map(certificateModelConverter::toDto)
                .collect(Collectors.toList());
        if (giftCertificateDtos.isEmpty()) {
            throw new NotFoundException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        return giftCertificateDtos;
    }
}
