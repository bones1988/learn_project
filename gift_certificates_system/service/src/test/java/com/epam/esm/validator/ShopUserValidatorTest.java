package com.epam.esm.validator;

import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.impl.ShopUserRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ShopUserValidatorTest {
    private static final String VALID_NAME = "valid";
    private static final String INVALID_NAME = " ";

    @InjectMocks
    private ShopUserValidator shopUserValidator;
    @Mock
    private ShopUserRepositoryImpl shopUserRepository;

    @Before
    public void init() {
        Mockito.when(shopUserRepository.checkLogin(any())).thenReturn(0);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNullShopUser() {
        shopUserValidator.validateUserForSignUp(null);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNullNameOfUser() {
        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserValidator.validateUserForSignUp(shopUserDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNullLoginOfUser() {
        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserDto.setName(VALID_NAME);
        shopUserValidator.validateUserForSignUp(shopUserDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNullUserPassword() {
        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserDto.setName(VALID_NAME);
        shopUserDto.setLogin(VALID_NAME);
        shopUserValidator.validateUserForSignUp(shopUserDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForExistLogin() {
        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserDto.setName(VALID_NAME);
        shopUserDto.setLogin(VALID_NAME);
        Mockito.when(shopUserRepository.checkLogin(any())).thenReturn(1);
        shopUserValidator.validateUserForSignUp(shopUserDto);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTagDtoShouldThrowExceptionForNotNullUserRole() {
        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserDto.setName(VALID_NAME);
        shopUserDto.setLogin(VALID_NAME);
        shopUserDto.setPassword(VALID_NAME);
        shopUserDto.setRole("role");
        shopUserValidator.validateUserForSignUp(shopUserDto);
    }
}
