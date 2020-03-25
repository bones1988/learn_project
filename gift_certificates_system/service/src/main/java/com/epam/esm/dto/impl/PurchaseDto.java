package com.epam.esm.dto.impl;

import com.epam.esm.model.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * class for dat transfering purchases
 */
public class PurchaseDto {
    private long id;
    private BigDecimal price;
    private LocalDateTime buyTime;
    private GiftCertificate giftCertificate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(LocalDateTime buyTime) {
        this.buyTime = buyTime;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }
}
