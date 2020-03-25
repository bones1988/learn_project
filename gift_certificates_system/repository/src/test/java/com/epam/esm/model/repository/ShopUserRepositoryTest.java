package com.epam.esm.model.repository;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.model.ShopUser;
import com.epam.esm.model.role.Role;
import com.epam.esm.repository.ShopUserRepository;
import com.epam.esm.repository.mybatis.mapper.ShopUserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@ContextConfiguration(classes = TestConfig.class)
public class ShopUserRepositoryTest {
    private static final long ID = 1L;
    private static final String USER_NAME = "user";
    private static final String USER_ROLE = "USER";

    private ShopUser shopUser;
    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private ShopUserRepository shopUserRepository;

    @Before
    public void init() {
        shopUser = new ShopUser();
        shopUser.setId(ID);
        shopUser.setName(USER_NAME);
        shopUser.setLogin(USER_NAME);
        shopUser.setPassword(USER_NAME);
        shopUser.setRole(Role.USER);
        shopUser.setActive(true);
    }

    @Test
    public void testGetAllShouldReturnFirstUser() {
        List<ShopUser> actual = shopUserRepository.getAll(5, 0);
        Assert.assertEquals(Arrays.asList(shopUser), actual);
    }

    @Test
    public void testGetUserDetailsShouldReturnFirstUser() {
        ShopUser actual = shopUserRepository.getByLogin(USER_NAME);
        Assert.assertEquals(shopUser, actual);
    }

    @Test
    public void testCheckLoginShouldReturnOne() {
        int actual = shopUserRepository.checkLogin(USER_NAME);
        Assert.assertEquals(1, actual);
    }
}
