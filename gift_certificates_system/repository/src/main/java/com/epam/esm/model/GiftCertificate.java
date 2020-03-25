package com.epam.esm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class of certificate
 */
public class GiftCertificate extends AbstractModel {
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private int duration;
    private Boolean active;
    private Set<Tag> tags;

    public GiftCertificate() {
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
     * Getter for price
     *
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
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
     * Getter of update date
     *
     * @return creation date
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
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
     * Setter for Description field
     *
     * @param description string description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Setter for creation date
     *
     * @param createDate date to be set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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
     * Setter of duration
     *
     * @param duration duration for setting
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Setter of tag set
     *
     * @param tags tag set to set
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Getter of tag set
     *
     * @return tag set
     */
    public Set<Tag> getTags() {
        return tags;
    }

    public Boolean isActive() {
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
        if (!(o instanceof GiftCertificate)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        GiftCertificate that = (GiftCertificate) o;
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
