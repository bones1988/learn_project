package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.GiftCertificateModelConverter;
import com.epam.esm.dto.converter.PurchaseModelConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.PurchaseDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.ShopUser;
import com.epam.esm.repository.ShopUserRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.PurchaseRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class PurchaseServiceImplTest {
    private static final String USER_NAME = "user";
    private static final long FIRST_ID = 1L;

    private static final long FIRST_CERTIFICATE_ID = 1L;
    private static final String FIRST_CERTIFICATE_NAME = "first certificate";
    private static final String FIRST_CERTIFICATE_DESCRIPTION = "description first certificate";
    private static final BigDecimal FIRST_CERTIFICATE_PRICE = BigDecimal.ONE;
    private static final LocalDateTime FIRST_CERTIFICATE_DATE =
            LocalDateTime.of(1, Month.JANUARY, 1, 1, 1, 1, 1);
    private static final int FIRST_CERTIFICATE_DURATION = 1;

    private ShopUser shopUser;
    private Purchase purchase;
    private PurchaseDto purchaseDto;
    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;


    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    @Mock
    private ShopUserRepository shopUserRepository;
    @Mock
    private GiftCertificateRepositoryImpl giftCertificateRepository;
    @Mock
    private PurchaseRepositoryImpl purchaseRepository;
    @Mock
    private PurchaseModelConverter purchaseModelConverter;
    @Mock
    private Authentication authentication;
    @Mock
    private GiftCertificateModelConverter certificateModelConverter;

    @Before
    public void init() {
        shopUser = new ShopUser();
        shopUser.setName(USER_NAME);
        shopUser.setId(FIRST_ID);

        giftCertificate = new GiftCertificate();
        giftCertificate.setId(FIRST_CERTIFICATE_ID);
        giftCertificate.setName(FIRST_CERTIFICATE_NAME);
        giftCertificate.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        giftCertificate.setPrice(FIRST_CERTIFICATE_PRICE);
        giftCertificate.setCreateDate(FIRST_CERTIFICATE_DATE);
        giftCertificate.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        giftCertificate.setDuration(FIRST_CERTIFICATE_DURATION);
        giftCertificate.setActive(true);

        giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(FIRST_CERTIFICATE_ID);
        giftCertificateDto.setName(FIRST_CERTIFICATE_NAME);
        giftCertificateDto.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        giftCertificateDto.setPrice(FIRST_CERTIFICATE_PRICE);
        giftCertificateDto.setCreateDate(FIRST_CERTIFICATE_DATE);
        giftCertificateDto.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        giftCertificateDto.setDuration(FIRST_CERTIFICATE_DURATION);
        giftCertificateDto.setActive(true);

        purchase = new Purchase();
        purchase.setUserId(FIRST_ID);
        purchase.setPrice(FIRST_CERTIFICATE_PRICE);
        purchase.setGiftCertificate(giftCertificate);
        purchase.setBuyTime(FIRST_CERTIFICATE_DATE);

        purchaseDto = new PurchaseDto();
        purchaseDto.setPrice(FIRST_CERTIFICATE_PRICE);
        purchaseDto.setGiftCertificate(giftCertificate);
        purchaseDto.setBuyTime(FIRST_CERTIFICATE_DATE);

        Mockito.when(authentication.getName()).thenReturn(USER_NAME);
        Mockito.when(shopUserRepository.getByLogin(USER_NAME)).thenReturn(shopUser);
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(giftCertificate));
        Mockito.when(purchaseModelConverter.toDto(purchase)).thenReturn(purchaseDto);
        Mockito.when(purchaseRepository.buy(any())).thenReturn(purchase);
    }

    @Test
    public void testBuyShouldReturnPurchaseDto() {
        PurchaseDto actual = purchaseService.buy(FIRST_ID, authentication);
        Assert.assertEquals(purchaseDto, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testBuyShouldThrowException() {
        giftCertificate.setActive(false);
        PurchaseDto actual = purchaseService.buy(FIRST_ID, authentication);
        Assert.assertEquals(purchaseDto, actual);
    }

    @Test
    public void testGetUserPurchasesShouldReturnPurchaseDto() {
        Mockito.when(purchaseRepository.getUserPurchases(1, 5, 0)).thenReturn(Arrays.asList(purchase));
        List<PurchaseDto> expected = Arrays.asList(purchaseDto);
        List<PurchaseDto> actual = purchaseService.getUserPurchases(1, "5", "1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUserCertificatesShouldReturnCertificateDto() {
        Mockito.when(purchaseRepository.getUserCertificates(1, 5, 0)).thenReturn(Arrays.asList(giftCertificate));
        Mockito.when(certificateModelConverter.toDto(giftCertificate)).thenReturn(giftCertificateDto);
        List<GiftCertificateDto> expected = Arrays.asList(giftCertificateDto);
        List<GiftCertificateDto> actual = purchaseService.getUserCertificates(1, "5", "1");
        Assert.assertEquals(expected, actual);
    }
}
