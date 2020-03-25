package com.epam.esm.validator;

import com.epam.esm.exception.ValidatorException;
import com.epam.esm.token.ShopUserCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CredentialsValidatorTest {
    private static final String VALID_NAME = "valid";
    private static final String INVALID_NAME = " ";

    @InjectMocks
    private CredentialsValidator credentialsValidator;

    @Test(expected = ValidatorException.class)
    public void testValidateUserForLoginShouldThrowExceptionForNullCredential() {
        credentialsValidator.validateUserForLogin(null);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateUserForLoginShouldThrowExceptionForNullCredentialLogin() {
        ShopUserCredentials shopUserCredentials = new ShopUserCredentials();
        credentialsValidator.validateUserForLogin(shopUserCredentials);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateUserForLoginShouldThrowExceptionForIncorrectCredentialLogin() {
        ShopUserCredentials shopUserCredentials = new ShopUserCredentials();
        shopUserCredentials.setLogin(INVALID_NAME);
        credentialsValidator.validateUserForLogin(shopUserCredentials);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateUserForLoginShouldThrowExceptionForNullPassword() {
        ShopUserCredentials shopUserCredentials = new ShopUserCredentials();
        shopUserCredentials.setLogin(VALID_NAME);
        credentialsValidator.validateUserForLogin(shopUserCredentials);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateUserForLoginShouldThrowExceptionForInvalidPassword() {
        ShopUserCredentials shopUserCredentials = new ShopUserCredentials();
        shopUserCredentials.setLogin(VALID_NAME);
        shopUserCredentials.setPassword(INVALID_NAME);
        credentialsValidator.validateUserForLogin(shopUserCredentials);
    }
}
