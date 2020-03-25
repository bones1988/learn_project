package com.epam.esm.dto.converter;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
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

@RunWith(SpringRunner.class)
public class GiftCertificateModelConverterTest {
    private static final long FIRST_CERTIFICATE_ID = 1L;
    private static final String FIRST_CERTIFICATE_NAME = "first certificate";
    private static final String FIRST_CERTIFICATE_DESCRIPTION = "description first certificate";
    private static final BigDecimal FIRST_CERTIFICATE_PRICE = BigDecimal.ONE;

    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;

    @InjectMocks
    private GiftCertificateModelConverter giftCertificateModelConverter;
    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(FIRST_CERTIFICATE_ID);
        giftCertificate.setName(FIRST_CERTIFICATE_NAME);
        giftCertificate.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        giftCertificate.setPrice(FIRST_CERTIFICATE_PRICE);

        giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(FIRST_CERTIFICATE_ID);
        giftCertificateDto.setName(FIRST_CERTIFICATE_NAME);
        giftCertificateDto.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        giftCertificateDto.setPrice(FIRST_CERTIFICATE_PRICE);

        Mockito.when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn(giftCertificateDto);
        Mockito.when(modelMapper.map(giftCertificateDto, GiftCertificate.class)).thenReturn(giftCertificate);
    }

    @Test
    public void testToDtoShouldReturnGiftCertificateDto() {
        GiftCertificateDto actual = giftCertificateModelConverter.toDto(giftCertificate);
        Assert.assertEquals(giftCertificateDto, actual);
    }

    @Test
    public void testToModelShouldReturnGiftCertificate() {
        GiftCertificate actual = giftCertificateModelConverter.toModel(giftCertificateDto);
        Assert.assertEquals(giftCertificate, actual);
    }
}
