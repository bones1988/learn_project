package com.epam.esm.validator;

import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.impl.ShopUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Class for validating input user DTOs
 */
@Component
public class ShopUserValidator {
    private static final String REGEX_FOR_TEXT_FIELDS = "\\w{2,16}";
    private ShopUserRepositoryImpl shopUserRepository;

    @Autowired
    public ShopUserValidator(ShopUserRepositoryImpl shopUserRepository) {
        this.shopUserRepository = shopUserRepository;
    }

    /**
     * @param shopUserDto dto to validate
     */
    public void validateUserForSignUp(ShopUserDto shopUserDto) {
        if (shopUserDto == null) {
            throw new ValidatorException("", ErrorCode.NULL_USER_INPUT);
        }
        if(shopUserDto.getName()==null||!shopUserDto.getName().matches(REGEX_FOR_TEXT_FIELDS)) {
            throw new ValidatorException(shopUserDto.getName(), ErrorCode.WRONG_FOR_LOGIN);
        }
        String login = shopUserDto.getLogin();
        if (login == null || !login.matches(REGEX_FOR_TEXT_FIELDS)) {
            throw new ValidatorException(login, ErrorCode.WRONG_FOR_NAME);
        }
        if (shopUserRepository.checkLogin(login) != 0) {
            throw new ValidatorException(login, ErrorCode.LOGIN_IS_PRESENT);
        }
        if(shopUserDto.getPassword()==null||!shopUserDto.getPassword().matches(REGEX_FOR_TEXT_FIELDS)) {
            throw new ValidatorException(shopUserDto.getPassword(), ErrorCode.WRONG_FOR_PASS);
        }
        if(shopUserDto.getRole()!=null) {
            throw new ValidatorException("", ErrorCode.WRONG_FOR_ROLE);
        }
    }
}
