package com.epam.esm.validator;

import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.exception.ErrorCode;
import org.springframework.stereotype.Component;

/**
 * Class for validating input tag DTOs
 */
@Component
public class TagValidator {
    private static final String REGEX_FOR_NAME = "\\w{2,16}";

    /**
     * Method validates tagDto
     *
     * @param tagDto inout tagDto
     */
    public void validateTagDto(TagDto tagDto) {
        if (tagDto == null) {
            throw new ValidatorException("null", ErrorCode.NULL_INPUT_TAG);
        }
        if (tagDto.getName() == null) {
            throw new ValidatorException("null", ErrorCode.INCORRECT_TAG_NAME);
        }
        if (!validateName(tagDto.getName())) {
            throw new ValidatorException(tagDto.getName(), ErrorCode.INCORRECT_TAG_NAME);
        }
    }

    private boolean validateName(String name) {
        return name.matches(REGEX_FOR_NAME);
    }
}
