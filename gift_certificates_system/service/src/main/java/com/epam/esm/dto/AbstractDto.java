package com.epam.esm.dto;

import java.util.Objects;

/**
 * Class of abstract data transfer objects
 */
public abstract class AbstractDto {
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
     * Setter for description
     *
     * @param id id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * Setter for name
     *
     * @param name id to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method to compare objects
     *
     * @param o object to compare with
     * @return boolean result of comparing
     */


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDto)) {
            return false;
        }
        AbstractDto that = (AbstractDto) o;
        return Objects.equals(name, that.name);
    }

    /**
     * hash code of abstract dto
     *
     * @return hash code of abstract dto
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
