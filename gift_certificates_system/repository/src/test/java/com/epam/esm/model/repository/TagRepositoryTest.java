package com.epam.esm.model.repository;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ModelRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.TagFilter;
import com.epam.esm.repository.mybatis.mapper.TagMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@ContextConfiguration(classes = TestConfig.class)
public class TagRepositoryTest {
    private static final long FIRST_ID = 1L;
    private static final long SECOND_ID = 2L;
    private static final String FIRST_TAG_NAME = "first";
    private static final String SECOND_TAG_NAME = "second";

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    @Qualifier("tagRepositoryImpl")
    private ModelRepository<Tag> tagRepository;
    private Tag firstTag;
    private Tag secondTag;
    private List<Tag> tagList;

    @Before
    public void init() {
        firstTag = new Tag();
        firstTag.setId(FIRST_ID);
        firstTag.setName(FIRST_TAG_NAME);

        secondTag = new Tag();
        secondTag.setId(SECOND_ID);
        secondTag.setName(SECOND_TAG_NAME);

        tagList = Arrays.asList(firstTag, secondTag);
    }

    @Test
    public void testQueryShouldReturnTwoTagsForEmptyFilter() {
        AbstractFilter tagFilter = new TagFilter();
        List<Tag> actual = tagRepository.query(tagFilter);
        Assert.assertEquals(tagList, actual);
    }

    @Test
    public void testRemoveShouldReturnOne() {
        int actual = tagRepository.remove(2);
        Assert.assertEquals(1, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdateShouldThrowException() {
        tagRepository.update(firstTag);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAssignTagToCertificateShouldThrowException() {
        tagRepository.assignTagToCertificate(FIRST_ID, FIRST_ID);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnAssignTagsShouldThrowException() {
        tagRepository.unAssignTags(FIRST_ID);
    }
}
