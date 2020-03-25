package com.epam.esm.service;

import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.filter.TagFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
public class TagFilterMakerTest {
    private Map<String, String> validParamsMap;
    private Map<String, String> inValidParamsMap;

    @InjectMocks
    private TagFilterMaker tagFilterMaker;

    @Before
    public void init() {
        validParamsMap = new HashMap<>();
        validParamsMap.put("name", "name");
        validParamsMap.put("pageSize", "5");
        validParamsMap.put("page", "1");

        inValidParamsMap = new HashMap<>();
        inValidParamsMap.put("not name", "not name");
    }

    @Test
    public void testMakeFilterShouldReturnFilterWithNameName() {
        TagFilter expected = new TagFilter();
        expected.setName("name");
        expected.setLimit(5);
        expected.setOffset(0);
        TagFilter actual = tagFilterMaker.makeFilter(validParamsMap);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ValidatorException.class)
    public void testMakeFilterShouldThrowExceptionForInvalidParamsMap() {
        TagFilter actual = tagFilterMaker.makeFilter(inValidParamsMap);
    }
}
