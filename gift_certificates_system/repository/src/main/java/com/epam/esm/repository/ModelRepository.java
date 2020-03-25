package com.epam.esm.repository;

import com.epam.esm.model.AbstractModel;
import com.epam.esm.repository.filter.AbstractFilter;

import java.util.List;

/**
 * Interface for repository
 */
public interface ModelRepository<T extends AbstractModel> {
    /**
     * Method for getting entities according specification
     *
     * @param filter entity of query rule
     * @return list of valid entities according specification
     */
    List<T> query(AbstractFilter filter);

    /**
     * Method for saving entity in database
     *
     * @param t entity to save
     * @return saved entity
     */
    long add(T t);

    /**
     * Method for removing entity by id
     *
     * @param id id of entity to delete
     * @return 0 if entity not found and 1 if found and deleted
     */
    int remove(long id);

    /**
     * Method for updating entity
     *
     * @param t entity to set
     * @return setted entity
     */
    T update(T t);

    /**
     * Method for assigning tags to certificate in database
     *
     * @param certificateId certificate to assign
     * @param tagId         tag to assign
     */
    void assignTagToCertificate(long certificateId, long tagId);

    void unAssignTags(long certificateId);
}
