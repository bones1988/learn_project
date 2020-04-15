package com.epam.esm.certificate;

import com.epam.esm.tag.Tag;
import com.epam.esm.tag.TagGenerator;
import com.epam.esm.tag.TagGeneratorImpl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CertificateGeneratorImpl implements CertificateGenerator {
    private static final String NAME_FOR_CORRECT_CERTIFICATES = "Correct";
    private static final String DESCRIPTION_FOR_CORRECT_CERTIFICATES = "Correct description";
    private static final BigDecimal PRICE_FOR_CORRECT_CERTIFICATES = BigDecimal.TEN;
    private static final int DURATION_FOR_CORRECT_CERTIFICATE = 10;
    private static final int CERTIFICATES_PER_FILE = 3;
    private static final int DURATION_FOR_INCORRECT_CERTIFICATE = -10;

    @Override
    public List<GiftCertificate> generateCertificates(String namePostfix) {
        List<GiftCertificate> certificateList = new ArrayList<>();
        for (int i = 0; i < CERTIFICATES_PER_FILE; i++) {
            GiftCertificate giftCertificate = new GiftCertificate();
            TagGenerator tagGenerator = new TagGeneratorImpl();
            Tag tag = tagGenerator.generateTag(namePostfix + i);
            Set<Tag> tagSet = new HashSet<>(Arrays.asList(tag));
            giftCertificate.setName(NAME_FOR_CORRECT_CERTIFICATES + namePostfix + i);
            giftCertificate.setDescription(DESCRIPTION_FOR_CORRECT_CERTIFICATES);
            giftCertificate.setPrice(PRICE_FOR_CORRECT_CERTIFICATES);
            giftCertificate.setDuration(DURATION_FOR_CORRECT_CERTIFICATE);
            giftCertificate.setTags(tagSet);
            certificateList.add(giftCertificate);
        }
        return certificateList;
    }

    @Override
    public List<GiftCertificate> generateIncorrectFieldName(String namePostfix) {
        List<GiftCertificate> result = generateCertificates(namePostfix);
        WrongCertificate wrongCertificate = new WrongCertificate();
        wrongCertificate.setName(NAME_FOR_CORRECT_CERTIFICATES);
        wrongCertificate.setWrong("wrong");
        wrongCertificate.setPrice(PRICE_FOR_CORRECT_CERTIFICATES);
        wrongCertificate.setError(DURATION_FOR_CORRECT_CERTIFICATE);
        result.add(wrongCertificate);
        return result;
    }

    @Override
    public List<GiftCertificate> generateCertificatesWithDbConstraints(String namePostfix) {
        return generateCertificates(namePostfix)
                .stream()
                .peek(giftCertificate -> giftCertificate.setDuration(DURATION_FOR_INCORRECT_CERTIFICATE))
                .collect(Collectors.toList());
    }

    @Override
    public String generateIncorrectJson() {
        return "incorrect";
    }

    @Override
    public List<GiftCertificate> generateCertificateJsonWithNonValidBean(String namePostfix) {
        List<GiftCertificate> certificateList = generateCertificates(namePostfix);
        return certificateList
                .stream()
                .peek(giftCertificate -> {
                    giftCertificate.getTags()
                            .forEach(tag -> tag.setName("s"));
                })
                .collect(Collectors.toList());
    }
}
