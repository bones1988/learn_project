package com.epam.esm.certificate;

import java.util.List;

public interface CertificateGenerator {
    List<GiftCertificate> generateCertificates(String namePostfix);

    List<GiftCertificate> generateIncorrectFieldName(String namePostfix);

    List<GiftCertificate> generateCertificatesWithDbConstraints(String namePostfix);

    String generateIncorrectJson();

    List<GiftCertificate> generateCertificateJsonWithNonValidBean(String namePostfix);
}
