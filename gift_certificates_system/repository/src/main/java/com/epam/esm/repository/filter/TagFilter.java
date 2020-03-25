package com.epam.esm.repository.filter;

import java.util.Objects;

/**
 * class for filtering tags
 */
public class TagFilter extends AbstractFilter {
    private long certificateId;

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagFilter)) return false;
        if (!super.equals(o)) return false;
        TagFilter tagFilter = (TagFilter) o;
        return certificateId == tagFilter.certificateId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), certificateId);
    }
}
