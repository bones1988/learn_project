package com.epam.esm.service;

import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.dto.impl.TokenDto;
import com.epam.esm.token.ShopUserCredentials;
import com.epam.esm.token.Token;

import java.util.List;

/**
 * interface for user service
 */
public interface ShopUserService {
    /**
     * @return list of users
     */
    List<ShopUserDto> getAll(String pageSize, String page);

    /**
     * @param login login for searching
     * @return user
     */
    ShopUserDto getByLogin(String login);

    /**
     * @param shopUserDto user
     * @return registered user
     */
    ShopUserDto signUp(ShopUserDto shopUserDto);

    /**
     * @param credentials login and pass
     * @return tolen
     */
    TokenDto authenticate(ShopUserCredentials credentials);

    TokenDto refresh(TokenDto refreshToken);
}
