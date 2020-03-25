package com.epam.esm.validator;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.token.ShopUserCredentials;
import org.springframework.stereotype.Component;

/**
 * class for validate credentials
 */
@Component
public class CredentialsValidator {
    private static final String REGEX_FOR_TEXT_FIELDS = "\\w{2,16}";

    /**
     * @param credentials input credentials
     */
    public void validateUserForLogin(ShopUserCredentials credentials) {
        if (credentials == null) {
            throw new ValidatorException("", ErrorCode.NULL_CRED_INPUT);
        }
        if(credentials.getLogin()==null||!credentials.getLogin().matches(REGEX_FOR_TEXT_FIELDS)) {
            throw new ValidatorException(credentials.getLogin(), ErrorCode.WRONG_LOGIN_FOR_LOGIN);
        }
        if(credentials.getPassword()==null||!credentials.getPassword().matches(REGEX_FOR_TEXT_FIELDS)) {
            throw new ValidatorException(credentials.getPassword(), ErrorCode.WRONG_PASS_FOR_LOGIN);
        }
    }
}
