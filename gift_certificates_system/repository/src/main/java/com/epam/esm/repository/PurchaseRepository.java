package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;

import java.util.List;

public interface PurchaseRepository {
    List<Purchase> getUserPurchases(long id, int limit, int offset);

    Purchase buy(Purchase purchase);

    List<GiftCertificate> getUserCertificates(long id, int limit, int offset);
}
