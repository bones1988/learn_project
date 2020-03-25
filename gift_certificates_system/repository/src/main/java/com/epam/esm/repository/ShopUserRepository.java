package com.epam.esm.repository;

import com.epam.esm.model.ShopUser;

import java.util.List;

/**
 * Interface for user repository
 */
public interface ShopUserRepository {

    /**
     * @return list of users
     */
    List<ShopUser> getAll(int limit, int offset);

    /**
     * @param login login
     * @return user by login
     */
    ShopUser getByLogin(String login);

    /**
     * @param shopUser users entity
     * @return saved user
     */
    ShopUser create(ShopUser shopUser);

    /**
     * @param login login for check
     * @return number of existing
     */
    int checkLogin(String login);
}
