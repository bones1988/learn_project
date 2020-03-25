package com.epam.esm.validator;

import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ValidatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TagValidatorTest {
    private static final String VALID_NAME = "valid";
    private static final String INVALID_NAME = " ";

    @InjectMocks
    TagValidator tagValidator;

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNullTagDto() {
        tagValidator.validateTagDto(null);
    }


    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNullTagDtoName() {
        TagDto tagDto = new TagDto();
        tagValidator.validateTagDto(tagDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForIncorrectTagName() {
        TagDto tagDto = new TagDto();
        tagDto.setName(INVALID_NAME);
        tagValidator.validateTagDto(tagDto);
    }
}
