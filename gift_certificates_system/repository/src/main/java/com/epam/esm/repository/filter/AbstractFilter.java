package com.epam.esm.repository.filter;

import java.util.Objects;

/**
 * abstract class of filters
 */
public abstract class AbstractFilter {
    private static final int DEFAULT_LIMIT = 5;
    private static final int DEFAULT_OFFSET = 0;
    private long id;
    private String name;

    private int limit;
    private int offset;

    private boolean popularTagTrigger;

    public AbstractFilter() {
        limit = DEFAULT_LIMIT;
        offset = DEFAULT_OFFSET;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isPopularTagTrigger() {
        return popularTagTrigger;
    }

    public void setPopularTagTrigger(boolean popularTagTrigger) {
        this.popularTagTrigger = popularTagTrigger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFilter)) return false;
        AbstractFilter filter = (AbstractFilter) o;
        return id == filter.id &&
                limit == filter.limit &&
                offset == filter.offset &&
                popularTagTrigger == filter.popularTagTrigger &&
                Objects.equals(name, filter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, limit, offset, popularTagTrigger);
    }
}
