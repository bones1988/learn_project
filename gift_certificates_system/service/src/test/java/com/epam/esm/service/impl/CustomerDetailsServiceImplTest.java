package com.epam.esm.service.impl;

import com.epam.esm.model.ShopUser;
import com.epam.esm.model.role.Role;
import com.epam.esm.repository.ShopUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
public class CustomerDetailsServiceImplTest {
    private static final long ID = 1L;
    private static final String USER_NAME = "admin";
    private static final String USER_PASS = "admin";
    private static final String USER_ROLE = "ADMINISTRATOR";
    private ShopUser shopUser;
    private User user;

    @InjectMocks
    private CustomerDetailsServiceImpl customerDetailsService;
    @Mock
    private ShopUserRepository shopUserRepository;

    @Before
    public void init() {
        shopUser = new ShopUser();
        shopUser.setId(ID);
        shopUser.setName(USER_NAME);
        shopUser.setLogin(USER_NAME);
        shopUser.setPassword(USER_PASS);
        shopUser.setRole(Role.ADMINISTRATOR);

        user = new User(USER_NAME, USER_PASS,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE)));
    }

    @Test
    public void testLoadUserByUsernameShouldReturnUser() {
        Mockito.when(shopUserRepository.getByLogin(USER_NAME)).thenReturn(shopUser);
        UserDetails actual = customerDetailsService.loadUserByUsername(USER_NAME);
        Assert.assertEquals(user, actual);
    }
}
