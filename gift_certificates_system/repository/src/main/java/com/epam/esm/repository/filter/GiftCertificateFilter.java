package com.epam.esm.repository.filter;

import java.util.List;
import java.util.Objects;

/**
 * class for filtering certificates
 */
public class GiftCertificateFilter extends AbstractFilter {
    private String description;
    private List<String> nameParts;
    private List<String> descParts;
    private List<String> tagNames;
    private List<String> sortParams;

    @Override
    public String getName() {
        return super.getName() == null ? null : '%' + super.getName() + '%';
    }

    public String getDescription() {
        return description == null ? null : '%' + description + '%';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNameParts() {
        return nameParts;
    }

    public void setNameParts(List<String> nameParts) {
        this.nameParts = nameParts;
    }

    public List<String> getDescParts() {
        return descParts;
    }

    public void setDescParts(List<String> descParts) {
        this.descParts = descParts;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public List<String> getSortParams() {
        return sortParams;
    }

    public void setSortParams(List<String> sortParams) {
        this.sortParams = sortParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateFilter)) return false;
        if (!super.equals(o)) return false;
        GiftCertificateFilter that = (GiftCertificateFilter) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(nameParts, that.nameParts) &&
                Objects.equals(descParts, that.descParts) &&
                Objects.equals(tagNames, that.tagNames) &&
                Objects.equals(sortParams, that.sortParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, nameParts, descParts, tagNames, sortParams);
    }
}
