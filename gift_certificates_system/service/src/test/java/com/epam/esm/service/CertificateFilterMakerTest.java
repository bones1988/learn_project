package com.epam.esm.service;

import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
public class CertificateFilterMakerTest {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TAG = "tag";
    private static final String SORT = "sort";
    private static final int LIMIT = 5;
    private static final int OFFSET = 0;
    private Map<String, String> invalidParamsMap;
    private Map<String, String> validParamsMap;


    @InjectMocks
    private CertificateFilterMaker certificateFilterMaker;

    @Before
    public void init() {
        invalidParamsMap = new HashMap<>();
        invalidParamsMap.put("invalid key", "error");

        validParamsMap = new HashMap<>();
        validParamsMap.put(NAME, NAME);
        validParamsMap.put(DESCRIPTION, DESCRIPTION);
        validParamsMap.put(TAG, TAG);
        validParamsMap.put("pageSize", String.valueOf(LIMIT));
        validParamsMap.put("page", "1");
        validParamsMap.put(SORT, "id,iddesc,name,namedesc,description,descriptiondesc,price,pricedesc,create,createdesc," +
                "update,updatedesc,duration,durationdesc");
    }

    @Test(expected = ValidatorException.class)
    public void testMakeFilterShouldThrowExceptionForInvalidParamsMap() {
        certificateFilterMaker.makeFilter(invalidParamsMap);
    }

    @Test
    public void testMakeFilterShouldReturnFilter() {
        GiftCertificateFilter expected = new GiftCertificateFilter();
        expected.setNameParts(Arrays.asList("'%" + NAME + "%'"));
        expected.setDescParts(Arrays.asList("'%" + DESCRIPTION + "%'"));
        expected.setTagNames(Arrays.asList(TAG));
        expected.setLimit(LIMIT);
        expected.setOffset(OFFSET);
        expected.setSortParams(Arrays.asList("certificate.id", "certificate.id desc", "certificate.name", "certificate.name desc",
                "certificate.description", "certificate.description desc", "certificate.price", "certificate.price desc",
                "certificate.create_date", "certificate.create_date desc", "certificate.update_date", "certificate.update_date desc",
                "certificate.duration", "certificate.duration desc"));
        GiftCertificateFilter actual = certificateFilterMaker.makeFilter(validParamsMap);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ValidatorException.class)
    public void testMakeFilterShouldThrowException() {
        validParamsMap.put(SORT, "error");
        GiftCertificateFilter actual = certificateFilterMaker.makeFilter(validParamsMap);
    }

    @Test(expected = ValidatorException.class)
    public void testMakeFilterShouldThrowExceptionForNumberFormatException() {
        validParamsMap.put("page", "b");
        GiftCertificateFilter actual = certificateFilterMaker.makeFilter(validParamsMap);
    }

    @Test(expected = ValidatorException.class)
    public void testMakeFilterShouldThrowExceptionForIncorrectPafe() {
        validParamsMap.put("page", "-1");
        GiftCertificateFilter actual = certificateFilterMaker.makeFilter(validParamsMap);
    }
}
