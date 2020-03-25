package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.ShopUserModelConverter;
import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.model.ShopUser;
import com.epam.esm.model.role.Role;
import com.epam.esm.repository.impl.PurchaseRepositoryImpl;
import com.epam.esm.repository.impl.ShopUserRepositoryImpl;
import com.epam.esm.token.ShopUserCredentials;
import com.epam.esm.token.Token;
import com.epam.esm.token.TokenUtil;
import com.epam.esm.validator.CredentialsValidator;
import com.epam.esm.validator.ShopUserValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class ShopUserServiceImplTest {
    private static final long ID = 1L;
    private static final String USER_NAME = "admin";
    private static final String USER_PASS = "admin";
    private static final String USER_ROLE = "admin";

    @InjectMocks
    private ShopUserServiceImpl shopUserService;
    @Mock
    private ShopUserModelConverter shopUserModelConverter;
    @Mock
    private ShopUserRepositoryImpl shopUserRepositoryImpl;
    @Mock
    private PurchaseRepositoryImpl purchaseRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CustomerDetailsServiceImpl customerDetailsService;
    @Mock
    private TokenUtil tokenUtil;
    @Mock
    private ShopUserValidator shopUserValidator;
    @Mock
    CredentialsValidator credentialsValidator;

    private ShopUser shopUser;
    private User user;
    private ShopUserDto shopUserDto;
    private ShopUserCredentials shopUserCredentials;
    private Token token;

    @Before
    public void init() {
        shopUser = new ShopUser();
        shopUser.setId(ID);
        shopUser.setName(USER_NAME);
        shopUser.setLogin(USER_NAME);
        shopUser.setPassword(USER_PASS);
        shopUser.setRole(Role.ADMINISTRATOR);

        user = new User(USER_NAME, USER_PASS, Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE)));

        shopUserDto = new ShopUserDto();
        shopUserDto.setId(ID);
        shopUserDto.setName(USER_NAME);
        shopUserDto.setLogin(USER_NAME);
        shopUserDto.setPassword(USER_PASS);
        shopUserDto.setRole("ADMINISTRATOR");


        shopUserCredentials = new ShopUserCredentials();
        shopUserCredentials.setLogin(USER_NAME);
        shopUserCredentials.setPassword(USER_PASS);

        token = new Token();
        token.setToken("new");


        Mockito.when(shopUserModelConverter.toDto(shopUser)).thenReturn(shopUserDto);
        Mockito.when(shopUserRepositoryImpl.getAll(anyInt(), anyInt())).thenReturn(Arrays.asList(shopUser));
        Mockito.when(shopUserRepositoryImpl.getByLogin(USER_NAME)).thenReturn(shopUser);
        Mockito.when(purchaseRepository.getUserPurchases(anyLong(), anyInt(), anyInt())).thenReturn(Collections.emptyList());
        Mockito.doNothing().when(shopUserValidator).validateUserForSignUp(shopUserDto);
        Mockito.when(passwordEncoder.encode(any())).thenReturn(USER_PASS);
        Mockito.when(shopUserModelConverter.toModel(shopUserDto)).thenReturn(shopUser);
        Mockito.when(shopUserRepositoryImpl.create(shopUser)).thenReturn(shopUser);
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(null);
        Mockito.doNothing().when(credentialsValidator).validateUserForLogin(any());
        Mockito.when(customerDetailsService.loadUserByUsername(USER_NAME)).thenReturn(user);
        Mockito.when(tokenUtil.generateToken(user)).thenReturn("new");

    }


    @Test
    public void testGetAllShouldReturnAdminDto() {
        List<ShopUserDto> usersList = new ArrayList<>();
        usersList.add(shopUserDto);
        List<ShopUserDto> actual = shopUserService.getAll("5", "1");
        Assert.assertEquals(usersList, actual);
    }

    @Test
    public void testGetByLoginShouldReturnAdmin() {
        ShopUserDto actual = shopUserService.getByLogin(USER_NAME);
        Assert.assertEquals(shopUserDto, actual);
    }

    @Test
    public void testSignUpShouldReturnFirstUserDto() {
        ShopUserDto actual = shopUserService.signUp(shopUserDto);
        Assert.assertEquals(shopUserDto, actual);
    }
}
