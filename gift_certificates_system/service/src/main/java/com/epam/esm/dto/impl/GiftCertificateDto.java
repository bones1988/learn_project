package com.epam.esm.dto.impl;

import com.epam.esm.dto.AbstractDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class for transfering data of certificates
 */
public class GiftCertificateDto extends AbstractDto {
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private int duration;
    private boolean active;
    private Set<TagDto> tags;

    /**
     * Constructor initializes tag set
     */
    public GiftCertificateDto() {
        tags = new HashSet<>();
    }

    /**
     * Getter for description
     *
     * @return description field
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for Description field
     *
     * @param description string description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for price
     *
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter for price
     *
     * @param price price for sertting
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getter of creation date
     *
     * @return creation date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter for creation date
     *
     * @param createDate date to be set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter of update date
     *
     * @return creation date
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Setter for update date
     *
     * @param lastUpdateDate date to be set
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Getter of duration
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter of duration
     *
     * @param duration duration for setting
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter of tag set
     *
     * @return tag set
     */
    public Set<TagDto> getTags() {
        return tags;
    }

    /**
     * Setter of tag set
     *
     * @param tags tag set to set
     */
    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftCertificateDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        GiftCertificateDto that = (GiftCertificateDto) o;
        return duration == that.duration &&
                active == that.active &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, price, createDate, lastUpdateDate, duration, active, tags);
    }
}
