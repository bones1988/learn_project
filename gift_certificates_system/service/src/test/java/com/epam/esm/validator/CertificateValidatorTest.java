package com.epam.esm.validator;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ValidatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
public class CertificateValidatorTest {
    private static final String VALID_TEXT_FIELD = "valid";
    private static final String INVALID_TEXT_FIELD = " ";
    private static final BigDecimal VALID_PRICE = BigDecimal.ONE;
    private static final BigDecimal INVALID_PRICE = BigDecimal.ZERO;
    private static final int DURATION = 1;
    private TagDto invalidTag;

    @InjectMocks
    private CertificateValidator certificateValidator;

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForNullCertificate() {
        certificateValidator.validateCertificateForAdd(null);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForNullCertificateName() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForInvalidCertificateName() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(INVALID_TEXT_FIELD);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForNullCertificateDescription() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForInvalidCertificateDescription() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(INVALID_TEXT_FIELD);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForNullCertificatePrice() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForInvalidCertificatePrice() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        giftCertificateDto.setPrice(INVALID_PRICE);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForInvalidCertificateDuration() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        giftCertificateDto.setPrice(VALID_PRICE);
        giftCertificateDto.setDuration(-1);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoShouldThrowExceptionForInvalidTags() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        giftCertificateDto.setPrice(VALID_PRICE);
        giftCertificateDto.setDuration(DURATION);
        invalidTag = new TagDto();
        invalidTag.setName(INVALID_TEXT_FIELD);
        giftCertificateDto.setTags(new HashSet<>(Arrays.asList(invalidTag)));
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoForPatchShouldThrowExceptionForNullCertificate() {
        certificateValidator.validateCertificateForPatch(null);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoForPatchShouldThrowExceptionForIncorrectCertificateName() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(INVALID_TEXT_FIELD);
        certificateValidator.validateCertificateForPatch(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoForPatchShouldThrowExceptionForIncorrectCertificateDescription() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(INVALID_TEXT_FIELD);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoForPatchShouldThrowExceptionForInvalidCertificatePrice() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        giftCertificateDto.setPrice(INVALID_PRICE);
        certificateValidator.validateCertificateForPatch(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoForPatchShouldThrowExceptionForInvalidCertificateDuration() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        giftCertificateDto.setPrice(VALID_PRICE);
        giftCertificateDto.setDuration(-1);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateCertificateDtoForPatchShouldThrowExceptionForInvalidTags() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(VALID_TEXT_FIELD);
        giftCertificateDto.setDescription(VALID_TEXT_FIELD);
        giftCertificateDto.setPrice(VALID_PRICE);
        giftCertificateDto.setDuration(DURATION);
        invalidTag = new TagDto();
        invalidTag.setName(INVALID_TEXT_FIELD);
        giftCertificateDto.setTags(new HashSet<>(Arrays.asList(invalidTag)));
        certificateValidator.validateCertificateForPatch(giftCertificateDto);
    }
}
