package com.epam.esm.repository.impl;

import com.epam.esm.model.ShopUser;
import com.epam.esm.repository.ShopUserRepository;
import com.epam.esm.repository.mybatis.mapper.ShopUserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @see ShopUserRepository
 */
@Repository
public class ShopUserRepositoryImpl implements ShopUserRepository {

    private ShopUserMapper shopUserMapper;

    @Autowired
    public ShopUserRepositoryImpl(ShopUserMapper shopUserMapper) {
        this.shopUserMapper = shopUserMapper;
    }

    /**
     * @see ShopUserRepository
     */
    @Override
    public List<ShopUser> getAll(int limit, int offset) {
        RowBounds rowBounds = new RowBounds(offset, limit);
        return shopUserMapper.getAll(rowBounds);
    }

    /**
     * @see ShopUserRepository
     */
    @Override
    public ShopUser getByLogin(String login) {
        return shopUserMapper.findUserByLogin(login);
    }

    /**
     * @see ShopUserRepository
     */
    public ShopUser create(ShopUser shopUser) {
        shopUserMapper.save(shopUser);
        return shopUser;
    }

    /**
     * @see ShopUserRepository
     */
    @Override
    public int checkLogin(String login) {
        return shopUserMapper.checkIfLoginExist(login);
    }
}
