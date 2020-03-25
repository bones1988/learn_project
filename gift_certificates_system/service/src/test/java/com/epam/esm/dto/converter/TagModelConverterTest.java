package com.epam.esm.dto.converter;

import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.model.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TagModelConverterTest {
    private static final long FIRST_ID = 1L;
    private static final String FIRST_NAME = "first";
    private Tag tag;
    private TagDto tagDto;

    @InjectMocks
    private TagModelConverter tagModelConverter;
    @Mock
    private ModelMapper modelMapper;


    @Before
    public void init() {
        tag = new Tag();
        tag.setId(FIRST_ID);
        tag.setName(FIRST_NAME);

        tagDto = new TagDto();
        tagDto.setId(FIRST_ID);
        tagDto.setName(FIRST_NAME);

        Mockito.when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        Mockito.when(modelMapper.map(tagDto, Tag.class)).thenReturn(tag);
    }

    @Test
    public void testToDtoShouldReturnTagDto() {
        TagDto actual = tagModelConverter.toDto(tag);
        Assert.assertEquals(tagDto, actual);
    }

    @Test
    public void testToModelShouldReturnTag() {
        Tag actual = tagModelConverter.toModel(tagDto);
        Assert.assertEquals(tag, actual);
    }
}
