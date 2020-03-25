package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.GiftCertificateModelConverter;
import com.epam.esm.dto.converter.TagModelConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.CertificateFilterMaker;
import com.epam.esm.validator.CertificateValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Gift certificate service class test
 */
@RunWith(SpringRunner.class)
public class GiftCertificateServiceImplTest {
    private static final long FIRST_ID = 1L;
    private static final String FIRST_NAME = "first";

    private static final long SECOND_ID = 2L;
    private static final String SECOND_NAME = "second";

    private static final long FIRST_CERTIFICATE_ID = 1L;
    private static final String FIRST_CERTIFICATE_NAME = "first certificate";
    private static final String FIRST_CERTIFICATE_DESCRIPTION = "description first certificate";
    private static final BigDecimal FIRST_CERTIFICATE_PRICE = BigDecimal.ONE;
    private static final LocalDateTime FIRST_CERTIFICATE_DATE =
            LocalDateTime.of(1, Month.JANUARY, 1, 1, 1, 1, 1);
    private static final int FIRST_CERTIFICATE_DURATION = 1;

    private static final long SECOND_CERTIFICATE_ID = 2L;
    private static final String SECOND_CERTIFICATE_NAME = "second certificate";
    private static final String SECOND_CERTIFICATE_DESCRIPTION = "description second certificate";
    private static final BigDecimal SECOND_CERTIFICATE_PRICE = BigDecimal.TEN;
    private static final LocalDateTime SECOND_CERTIFICATE_DATE =
            LocalDateTime.of(2, Month.FEBRUARY, 2, 2, 2, 2, 2);
    private static final int SECOND_CERTIFICATE_DURATION = 2;

    private Tag firstTag;
    private Tag secondTag;
    private TagDto firstTagDto;
    private TagDto secondTagDto;
    private GiftCertificate firstCertificate;
    private GiftCertificate secondCertificate;
    private GiftCertificateDto firstCertificateDto;
    private GiftCertificateDto secondCertificateDto;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateRepositoryImpl giftCertificateRepository;
    @Mock
    private GiftCertificateModelConverter giftCertificateModelConverter;
    @Mock
    private TagModelConverter tagModelConverter;
    @Mock
    private TagServiceImpl tagService;
    @Mock
    private CertificateValidator certificateValidator;
    @Mock
    private TagRepositoryImpl tagRepository;
    @Mock
    private CertificateFilterMaker certificateFilterMaker;

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

        firstCertificate = new GiftCertificate();
        firstCertificate.setId(FIRST_CERTIFICATE_ID);
        firstCertificate.setName(FIRST_CERTIFICATE_NAME);
        firstCertificate.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        firstCertificate.setPrice(FIRST_CERTIFICATE_PRICE);
        firstCertificate.setCreateDate(FIRST_CERTIFICATE_DATE);
        firstCertificate.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        firstCertificate.setDuration(FIRST_CERTIFICATE_DURATION);
        firstCertificate.setActive(true);
        Set<Tag> firstCertificateTagSet = new HashSet<>(Arrays.asList(firstTag));
        firstCertificate.setTags(firstCertificateTagSet);

        secondCertificate = new GiftCertificate();
        secondCertificate.setId(SECOND_CERTIFICATE_ID);
        secondCertificate.setName(SECOND_CERTIFICATE_NAME);
        secondCertificate.setDescription(SECOND_CERTIFICATE_DESCRIPTION);
        secondCertificate.setPrice(SECOND_CERTIFICATE_PRICE);
        secondCertificate.setCreateDate(SECOND_CERTIFICATE_DATE);
        secondCertificate.setLastUpdateDate(SECOND_CERTIFICATE_DATE);
        secondCertificate.setDuration(SECOND_CERTIFICATE_DURATION);
        Set<Tag> secondCertificateTagSet = new HashSet<>(Arrays.asList(firstTag, secondTag));
        secondCertificate.setTags(secondCertificateTagSet);

        firstCertificateDto = new GiftCertificateDto();
        firstCertificateDto.setId(FIRST_CERTIFICATE_ID);
        firstCertificateDto.setName(FIRST_CERTIFICATE_NAME);
        firstCertificateDto.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        firstCertificateDto.setPrice(FIRST_CERTIFICATE_PRICE);
        firstCertificateDto.setCreateDate(FIRST_CERTIFICATE_DATE);
        firstCertificateDto.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        firstCertificateDto.setDuration(FIRST_CERTIFICATE_DURATION);

        secondCertificateDto = new GiftCertificateDto();
        secondCertificateDto.setId(SECOND_CERTIFICATE_ID);
        secondCertificateDto.setName(SECOND_CERTIFICATE_NAME);
        secondCertificateDto.setDescription(SECOND_CERTIFICATE_DESCRIPTION);
        secondCertificateDto.setPrice(SECOND_CERTIFICATE_PRICE);
        secondCertificateDto.setCreateDate(SECOND_CERTIFICATE_DATE);
        secondCertificateDto.setLastUpdateDate(SECOND_CERTIFICATE_DATE);
        secondCertificateDto.setDuration(SECOND_CERTIFICATE_DURATION);
        Set<TagDto> secondCertificateTagDtoSet = new HashSet<>(Arrays.asList(firstTagDto, secondTagDto));
        secondCertificateDto.setTags(secondCertificateTagDtoSet);

        Mockito.doNothing().when(certificateValidator).validateCertificateForAdd(any());
        Mockito.doNothing().when(certificateValidator).validateCertificateForPatch(any());
        Mockito.when(giftCertificateModelConverter.toModel(any())).thenReturn(firstCertificate);
        Mockito.when(tagService.save(any())).thenReturn(firstTagDto);
        Mockito.when(tagModelConverter.toModel(any())).thenReturn(firstTag);
        Mockito.when(giftCertificateRepository.add(any())).thenReturn(1L);
        Mockito.when(giftCertificateModelConverter.toDto(any())).thenReturn(firstCertificateDto);

    }

    /**
     * Method checks that delete method in service calls delete method in repository
     */
    @Test
    public void testDeleteShouldCallDeleteMethod() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        giftCertificateService.delete(FIRST_CERTIFICATE_ID);
        verify(giftCertificateRepository, times(1)).update(firstCertificate);
    }

    @Test
    public void testSaveShouldReturnFirstCertificateDto() {
        GiftCertificateDto actual = giftCertificateService.save(firstCertificateDto);
        Assert.assertEquals(firstCertificateDto, actual);
    }

    @Test
    public void testDeleteShouldReturnOneWhenCallDelete() {
        Mockito.when(giftCertificateRepository.remove(1L)).thenReturn(1);
        int actual = giftCertificateRepository.remove(1L);
        Assert.assertEquals(1L, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteShouldThrowExceptionDelete() {
        Mockito.when(giftCertificateRepository.remove(1L)).thenReturn(0);
        giftCertificateService.delete(1L);
    }

    @Test
    public void testPatchShouldCallMethodUpdate() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        giftCertificateService.patch(firstCertificateDto, 1L);
        Mockito.verify(giftCertificateRepository).update(firstCertificate);
    }

    @Test
    public void testPatchShouldCallMethodUpdateForEmptyCertificate() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        giftCertificateService.patch(new GiftCertificateDto(), 1L);
        Mockito.verify(giftCertificateRepository).update(firstCertificate);
    }

    @Test(expected = NotFoundException.class)
    public void testPatchShouldThrowException() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        Mockito.when(giftCertificateRepository.update(any())).thenThrow(new RuntimeException());
        giftCertificateService.patch(firstCertificateDto, 1L);
        Mockito.verify(giftCertificateRepository).update(firstCertificate);
    }

    @Test
    public void testChangePriceShouldCallUpdateMethodInRepository() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        giftCertificateService.changePrice(1L, BigDecimal.ONE);
        Mockito.verify(giftCertificateRepository).update(firstCertificate);
    }

    @Test
    public void testGetByParamsShouldCallQueryMethod() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        Mockito.when(certificateFilterMaker.makeFilter(anyMap())).thenReturn(new GiftCertificateFilter());
        giftCertificateService.getByParams(new HashMap<String, String>());
        Mockito.verify(giftCertificateRepository).query(any());
    }

    @Test(expected = NotFoundException.class)
    public void testGetByParamsShouldThrowNotFoundException() {
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(certificateFilterMaker.makeFilter(anyMap())).thenReturn(new GiftCertificateFilter());
        giftCertificateService.getByParams(new HashMap<String, String>());
    }

    @Test
    public void testPutShouldReturnFirstTagDto() {
        Mockito.when(giftCertificateRepository.update(any())).thenReturn(firstCertificate);
        Mockito.when(giftCertificateRepository.query(any())).thenReturn(Arrays.asList(firstCertificate));
        GiftCertificateDto actual = giftCertificateService.put(firstCertificateDto, 1);
        Assert.assertEquals(firstCertificateDto, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testPutShouldThrowNotFoundException() {
        Mockito.when(giftCertificateRepository.update(any())).thenThrow(new RuntimeException("error"));
        GiftCertificateDto actual = giftCertificateService.put(firstCertificateDto, 1);
    }
}
