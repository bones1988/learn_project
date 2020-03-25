package com.epam.esm.validator;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Class for validating input Certificate DTOs
 */
@Component
public class CertificateValidator {
    private static final String REGEX_FOR_NAME = "\\w{2,16}";
    private static final String REGEX_FOR_DESCRIPTION = "[\\w\\s]{2,501}$";
    private static final double MIN_PRICE = 0.01D;
    private static final double MAX_PRICE = 9999;
    private static final int MIN_DURATION = 0;
    private static final int MAX_DURATION = 999;

    /**
     * Method validates tagDto for saving
     *
     * @param giftCertificateDto input gift certificate DTO for saving it in base
     */
    public void validateCertificateForAdd(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            throw new ValidatorException("null", ErrorCode.NULL_INPUT_CERTIFICATE);
        }

        if (giftCertificateDto.getName() == null || !validateName(giftCertificateDto.getName())) {
            throw new ValidatorException(giftCertificateDto.getName(), ErrorCode.INCORRECT_CERTIFICATE_NAME);
        }
        if (giftCertificateDto.getDescription() == null || !validateDescription(giftCertificateDto.getDescription())) {
            throw new ValidatorException(giftCertificateDto.getDescription(), ErrorCode.INCORRECT_CERTIFICATE_DESCRIPTION);
        }
        if (giftCertificateDto.getPrice() == null || !validatePrice(giftCertificateDto.getPrice())) {
            throw new ValidatorException(String.valueOf(giftCertificateDto.getPrice()), ErrorCode.INCORRECT_CERTIFICATE_PRICE);
        }
        if (!validateDuration(giftCertificateDto.getDuration())) {
            throw new ValidatorException(String.valueOf(giftCertificateDto.getDuration()), ErrorCode.INCORRECT_CERTIFICATE_DURATION);
        }
        if (giftCertificateDto.getTags() != null) {
            if (!validateTagSet(giftCertificateDto.getTags())) {
                throw new ValidatorException(null, ErrorCode.INCORRECT_TAGS_IN_CERTIFICATE);
            }
        }
    }

    /**
     * Method validates tagDto for saving
     *
     * @param giftCertificateDto input gift certificate DTO for updating existing certificate
     */
    public void validateCertificateForPatch(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            throw new ValidatorException("null", ErrorCode.NULL_INPUT_CERTIFICATE);
        }
        String name = giftCertificateDto.getName();
        if (name != null && !validateName(name)) {
            throw new ValidatorException(giftCertificateDto.getName(), ErrorCode.INCORRECT_CERTIFICATE_NAME);
        }

        String description = giftCertificateDto.getDescription();
        if (description != null && !validateDescription(description)) {
            throw new ValidatorException(giftCertificateDto.getDescription(), ErrorCode.INCORRECT_CERTIFICATE_DESCRIPTION);
        }

        BigDecimal price = giftCertificateDto.getPrice();
        if (price != null && !validatePrice(price)) {
            throw new ValidatorException(String.valueOf(giftCertificateDto.getPrice()), ErrorCode.INCORRECT_CERTIFICATE_PRICE);
        }

        if (!validateDuration(giftCertificateDto.getDuration())) {
            throw new ValidatorException(String.valueOf(giftCertificateDto.getDuration()), ErrorCode.INCORRECT_CERTIFICATE_DURATION);
        }
        Set<TagDto> tagDtoSet = giftCertificateDto.getTags();
        if (tagDtoSet != null) {
            throw new ValidatorException(null, ErrorCode.INCORRECT_TAGS_IN_CERTIFICATE);
        }
    }


    private boolean validateName(String name) {
        return name.matches(REGEX_FOR_NAME);
    }

    private boolean validateDescription(String description) {
        return description.matches(REGEX_FOR_DESCRIPTION);
    }

    private boolean validatePrice(BigDecimal price) {
        return price.compareTo(BigDecimal.valueOf(MIN_PRICE)) > 0 && price.compareTo(BigDecimal.valueOf(MAX_PRICE)) < 0;
    }

    private boolean validateDuration(int duration) {
        return duration >= MIN_DURATION && duration <= MAX_DURATION;
    }

    private boolean validateTagSet(Set<TagDto> tagset) {
        boolean valid = true;
        for (TagDto tagDto : tagset) {
            if (!tagDto.getName().matches(REGEX_FOR_NAME)) {
                valid = false;
                break;
            }
        }
        return valid;
    }
}
