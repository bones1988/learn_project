package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.repository.PurchaseRepository;
import com.epam.esm.repository.mybatis.mapper.PurchaseMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for purchases
 */
@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {
    @Autowired
    private PurchaseMapper purchaseMapper;

    public List<Purchase> getUserPurchases(long id, int limit, int offset) {
        RowBounds rowBounds = new RowBounds(offset, limit);
        return purchaseMapper.getUserPurchases(id, rowBounds);
    }

    public Purchase buy(Purchase purchase) {
        purchase.setBuyTime(LocalDateTime.now());
        purchaseMapper.savePurchase(purchase);
        return purchase;
    }

    @Override
    public List<GiftCertificate> getUserCertificates(long id, int limit, int offset) {
        RowBounds rowBounds = new RowBounds(offset, limit);
        return purchaseMapper.getUserCertificates(id, rowBounds);
    }
}
