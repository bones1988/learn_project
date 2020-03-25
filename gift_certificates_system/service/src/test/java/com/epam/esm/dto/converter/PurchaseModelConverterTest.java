package com.epam.esm.dto.converter;

import com.epam.esm.dto.impl.PurchaseDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@RunWith(SpringRunner.class)
public class PurchaseModelConverterTest {
    private static final long FIRST_ID = 1L;

    private static final long FIRST_CERTIFICATE_ID = 1L;
    private static final String FIRST_CERTIFICATE_NAME = "first certificate";
    private static final String FIRST_CERTIFICATE_DESCRIPTION = "description first certificate";
    private static final BigDecimal FIRST_CERTIFICATE_PRICE = BigDecimal.ONE;
    private static final LocalDateTime FIRST_CERTIFICATE_DATE =
            LocalDateTime.of(1, Month.JANUARY, 1, 1, 1, 1, 1);
    private static final int FIRST_CERTIFICATE_DURATION = 1;

    private Purchase purchase;
    private PurchaseDto purchaseDto;
    private GiftCertificate giftCertificate;

    @InjectMocks
    private PurchaseModelConverter purchaseModelConverter;
    @Mock
    private ModelMapper modelMapper;


    @Before
    public void init() {
        purchase = new Purchase();
        purchase.setUserId(FIRST_ID);
        purchase.setPrice(FIRST_CERTIFICATE_PRICE);
        purchase.setGiftCertificate(giftCertificate);
        purchase.setBuyTime(FIRST_CERTIFICATE_DATE);

        purchaseDto = new PurchaseDto();
        purchaseDto.setPrice(FIRST_CERTIFICATE_PRICE);
        purchaseDto.setGiftCertificate(giftCertificate);
        purchaseDto.setBuyTime(FIRST_CERTIFICATE_DATE);

        giftCertificate = new GiftCertificate();
        giftCertificate.setId(FIRST_CERTIFICATE_ID);
        giftCertificate.setName(FIRST_CERTIFICATE_NAME);
        giftCertificate.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        giftCertificate.setPrice(FIRST_CERTIFICATE_PRICE);
        giftCertificate.setCreateDate(FIRST_CERTIFICATE_DATE);
        giftCertificate.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        giftCertificate.setDuration(FIRST_CERTIFICATE_DURATION);
        giftCertificate.setActive(true);

        Mockito.when(modelMapper.map(purchase, PurchaseDto.class)).thenReturn(purchaseDto);
        Mockito.when(modelMapper.map(purchaseDto, Purchase.class)).thenReturn(purchase);
    }

    @Test
    public void testToDtoShouldReturnGiftPurchaseDto() {
        PurchaseDto actual = purchaseModelConverter.toDto(purchase);
        Assert.assertEquals(purchaseDto, actual);
    }

    @Test
    public void testToModelShouldReturnPurchase() {
        Purchase actual = purchaseModelConverter.toModel(purchaseDto);
        Assert.assertEquals(purchase, actual);
    }
}
