package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.TagModelConverter;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ModelRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.TagFilter;
import com.epam.esm.service.TagFilterMaker;
import com.epam.esm.validator.TagValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

/**
 * Tag certificate service class test
 */
@RunWith(SpringRunner.class)
public class TagServiceImplTest {
    private static final long FIRST_ID = 1L;
    private static final long SECOND_ID = 2L;
    private static final String FIRST_NAME = "first";
    private static final String SECOND_NAME = "second";
    private Tag firstTag;
    private Tag secondTag;
    private TagDto firstTagDto;
    private TagDto secondTagDto;
    private List<Tag> tagList;
    private List<TagDto> tagDtoList;

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagValidator tagValidator;
    @Mock
    private ModelRepository<Tag> tagRepository;
    @Mock
    private TagModelConverter tagModelConverter;
    @Mock
    private TagFilterMaker tagFilterMaker;
    @Mock
    TagFilter tagFilter;


    /**
     * Method for prepare test environment
     */
    @Before
    public void init() {
        firstTag = new Tag();
        firstTag.setId(FIRST_ID);
        firstTag.setName(FIRST_NAME);

        secondTag = new Tag();
        secondTag.setId(SECOND_ID);
        secondTag.setName(SECOND_NAME);

        firstTagDto = new TagDto();
        firstTagDto.setId(FIRST_ID);
        firstTagDto.setName(FIRST_NAME);

        secondTagDto = new TagDto();
        secondTagDto.setId(SECOND_ID);
        secondTagDto.setName(SECOND_NAME);

        tagList = new ArrayList<>(Arrays.asList(firstTag, secondTag));
        tagDtoList = new ArrayList<>(Arrays.asList(firstTagDto, secondTagDto));

        Mockito.when(tagModelConverter.toDto(firstTag)).thenReturn(firstTagDto);
        Mockito.when(tagModelConverter.toDto(secondTag)).thenReturn(secondTagDto);
        Mockito.when(tagModelConverter.toModel(firstTagDto)).thenReturn(firstTag);
        Mockito.when(tagFilterMaker.makeFilter(any())).thenReturn(tagFilter);
        Mockito.doNothing().when(tagValidator).validateTagDto(any());
    }

    /**
     * Method checks that get all method returns all tags
     */
    @Test
    public void testGetAllTagsShouldReturnTwoTagDtoListWhenCalledMethodGetByParamsWithBlankFilter() {
        AbstractFilter tagFilter = new TagFilter();
        Map<String, String> searchParams = new HashMap<>();
        Mockito.when(tagRepository.query(any())).thenReturn(tagList);
        List<TagDto> actual = tagService.getByParams(searchParams);
        Assert.assertEquals(tagDtoList, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetAllTagsShouldThrowException() {
        AbstractFilter tagFilter = new TagFilter();
        Map<String, String> searchParams = new HashMap<>();
        Mockito.when(tagRepository.query(any())).thenReturn(Collections.EMPTY_LIST);
        List<TagDto> actual = tagService.getByParams(searchParams);
        Assert.assertEquals(tagDtoList, actual);
    }

    /**
     * //     * Method checks that save tag returns exist tag
     * //
     */
    @Test
    public void testSaveShouldCallSaveAndReturnFirstTag() {
        Mockito.when(tagRepository.query(any())).thenReturn(tagList);
        Mockito.when(tagRepository.add(firstTag)).thenReturn(FIRST_ID);
        TagDto actual = tagService.save(firstTagDto);
        Assert.assertEquals(firstTagDto, actual);
    }

    @Test
    public void testSaveShouldSaveAndReturnFirstTag() {
        Mockito.when(tagRepository.add(firstTag)).thenReturn(FIRST_ID);
        Mockito.when(tagRepository.query(any())).thenReturn(Collections.EMPTY_LIST);
        TagDto actual = tagService.save(firstTagDto);
        Assert.assertEquals(firstTagDto, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testSaveShouldThrowException() {
        Mockito.when(tagRepository.add(firstTag)).thenReturn(FIRST_ID);
        Mockito.when(tagRepository.query(any())).thenThrow(new RuntimeException());
        TagDto actual = tagService.save(firstTagDto);
        Assert.assertEquals(firstTagDto, actual);
    }

    /**
     * Method checks that get by id method returns first tag for first id
     */
    @Test
    public void testGetTagByIdShouldReturnFirstTag() {
        Mockito.when(tagRepository.query(any())).thenReturn(tagList);
        TagDto actual = tagService.getById(FIRST_ID);
        Assert.assertEquals(firstTagDto, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetTagByIdShouldThrowException() {
        Mockito.when(tagRepository.query(any())).thenReturn(Collections.EMPTY_LIST);
        TagDto actual = tagService.getById(FIRST_ID);
        Assert.assertEquals(firstTagDto, actual);
    }

    /**
     * Method checks that remove tag should throw exception for incorrect id
     */
    @Test(expected = NotFoundException.class)
    public void testDeleteTagByIdShouldThrowNotFoundForRepositoryException() {
        Mockito.when(tagRepository.remove(FIRST_ID)).thenReturn(0);
        tagService.deleteById(FIRST_ID);
    }

    @Test
    public void testDeleteTagByIdShouldReturnOneForCorrectId() {
        Mockito.when(tagRepository.remove(FIRST_ID)).thenReturn(1);
        int actual = tagService.deleteById(FIRST_ID);
        Assert.assertEquals(1, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testTaskThrowNotFoundForRepositoryException() {
        Mockito.when(tagRepository.query(any())).thenReturn(Collections.EMPTY_LIST);
        tagService.getPopularTag();
    }

    @Test
    public void testTaskReturnOneForCorrectId() {
        Mockito.when(tagRepository.query(any())).thenReturn(tagList);
        TagDto actual = tagService.getPopularTag();
        Assert.assertEquals(firstTagDto, actual);
    }
}
