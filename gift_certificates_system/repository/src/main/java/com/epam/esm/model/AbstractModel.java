package com.epam.esm.model;

import java.util.Objects;

/**
 * Class of abstract model
 */
public abstract class AbstractModel {
    private long id;
    private String name;

    /**
     * Getter for id
     *
     * @return id field
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for name
     *
     * @return name field
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for id
     *
     * @param id long id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter for name
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractModel)) {
            return false;
        }
        AbstractModel that = (AbstractModel) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
