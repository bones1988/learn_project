package com.epam.esm.model.repository;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ModelRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

/**
 * Gift certificate repository class test
 */
@RunWith(SpringRunner.class)
@MybatisTest
@ContextConfiguration(classes = TestConfig.class)
public class GiftCertificateRepositoryTest {
    private static final long FIRST_ID = 1L;
    private static final long SECOND_ID = 2L;
    private static final String FIRST_TAG_NAME = "first";
    private static final String SECOND_TAG_NAME = "second";
    private static final long FIRST_CERTIFICATE_ID = 1;
    private static final String FIRST_CERTIFICATE_NAME = "first";
    private static final BigDecimal FIRST_CERTIFICATE_PRICE = new BigDecimal("1.00");
    private static final String FIRST_CERTIFICATE_DESCRIPTION = "description first certificate";
    private static final LocalDateTime FIRST_CERTIFICATE_DATE =
            LocalDateTime.of(2001, Month.JANUARY, 1, 1, 1, 1);
    private static final int FIRST_CERTIFICATE_DURATION = 11;

    @Autowired
    @Qualifier("giftCertificateRepositoryImpl")
    private ModelRepository<GiftCertificate> giftCertificateRepository;
    private GiftCertificate firstCertificate;
    private Tag firstTag;
    private Tag secondTag;

    /**
     * Method for prepare test environment
     */
    @Before
    public void init() {
        firstTag = new Tag();
        firstTag.setId(FIRST_ID);
        firstTag.setName(FIRST_TAG_NAME);

        secondTag = new Tag();
        secondTag.setId(SECOND_ID);
        secondTag.setName(SECOND_TAG_NAME);

        firstCertificate = new GiftCertificate();
        firstCertificate.setId(FIRST_CERTIFICATE_ID);
        firstCertificate.setName(FIRST_CERTIFICATE_NAME);
        firstCertificate.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        firstCertificate.setPrice(FIRST_CERTIFICATE_PRICE);
        firstCertificate.setCreateDate(FIRST_CERTIFICATE_DATE);
        firstCertificate.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        firstCertificate.setDuration(FIRST_CERTIFICATE_DURATION);
        firstCertificate.setActive(true);

        Set<Tag> firstTagSet = new HashSet<>();
        firstTagSet.add(firstTag);
        firstCertificate.setTags(firstTagSet);
    }

    /**
     * Method checks that remove method removes row and return 1
     */
    @Test
    public void testRemoveShouldReturnOneForDeletingFirstCertificate() {
        int actual = giftCertificateRepository.remove(FIRST_ID);
        Assert.assertEquals(1, actual);
    }

    /**
     * Method checks that query method returns certificate accordind to query
     */
    @Test
    public void testQueryShouldReturnFirstCertificateForGetCertificateByIdQuery() {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        filter.setId(FIRST_ID);
        GiftCertificate actual = giftCertificateRepository.query(filter)
                .get(0);
        Assert.assertEquals(firstCertificate, actual);
    }

    /**
     * Method checks that add method returns saved certificate
     */
    @Test
    public void testSetShouldChangeAndReturnSecondCertificateAsFirst() {
        GiftCertificate actual = giftCertificateRepository.update(firstCertificate);
        actual.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        Assert.assertEquals(firstCertificate, actual);
    }

    @Test
    public void testAssignTagToCertificateShouldAssignSecondTagToFirstCertificate() {
        AbstractFilter certificateFilter = new GiftCertificateFilter();
        certificateFilter.setId(FIRST_ID);
        GiftCertificate actual = giftCertificateRepository.query(certificateFilter).get(0);
        Assert.assertEquals(firstCertificate, actual);
        firstCertificate.getTags().add(secondTag);
        giftCertificateRepository.assignTagToCertificate(FIRST_ID, SECOND_ID);
        actual = giftCertificateRepository.query(certificateFilter).get(0);
        Assert.assertEquals(firstCertificate, actual);
    }

    @Test
    public void testUnAssignTagToCertificateShouldUnAssignTagsFromFirstCertificate() {
        AbstractFilter certificateFilter = new GiftCertificateFilter();
        certificateFilter.setId(FIRST_ID);
        GiftCertificate giftCertificate = giftCertificateRepository.query(certificateFilter).get(0);
        Set<Tag> actual = giftCertificate.getTags();
        Assert.assertEquals(firstCertificate.getTags(), actual);
        Set<Tag> expected = new HashSet<>();
        giftCertificateRepository.unAssignTags(giftCertificate.getId());
        giftCertificate = giftCertificateRepository.query(certificateFilter).get(0);
        actual = giftCertificate.getTags();
        Assert.assertEquals(expected, actual);
    }
}
