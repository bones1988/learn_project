package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.ShopUserModelConverter;
import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.dto.impl.TokenDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.model.ShopUser;
import com.epam.esm.model.role.Role;
import com.epam.esm.repository.ShopUserRepository;
import com.epam.esm.repository.impl.ShopUserRepositoryImpl;
import com.epam.esm.service.ShopUserService;
import com.epam.esm.token.ShopUserCredentials;
import com.epam.esm.token.Token;
import com.epam.esm.token.TokenUtil;
import com.epam.esm.validator.CredentialsValidator;
import com.epam.esm.validator.ShopUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @see ShopUserService
 */
@Service
public class ShopUserServiceImpl implements ShopUserService {
    private static final int MAXIMUM_PAGESIZE = 50;
    private ShopUserModelConverter shopUserModelConverter;
    private ShopUserRepository shopUserRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private CustomerDetailsServiceImpl customerDetailsService;
    private TokenUtil tokenUtil;
    private ShopUserValidator shopUserValidator;
    private CredentialsValidator credentialsValidator;

    @Autowired
    public ShopUserServiceImpl(ShopUserModelConverter shopUserModelConverter, ShopUserRepositoryImpl shopUserRepository,
                               PasswordEncoder passwordEncoder,
                               AuthenticationManager authenticationManager,
                               CustomerDetailsServiceImpl customerDetailsService,
                               TokenUtil tokenUtil, ShopUserValidator shopUserValidator,
                               CredentialsValidator credentialsValidator) {
        this.shopUserModelConverter = shopUserModelConverter;
        this.shopUserRepository = shopUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customerDetailsService = customerDetailsService;
        this.tokenUtil = tokenUtil;
        this.shopUserValidator = shopUserValidator;
        this.credentialsValidator = credentialsValidator;
    }

    /**
     * @see ShopUserService
     */
    @Override
    public List<ShopUserDto> getAll(String pageSize, String page) {
        int limit;
        int offset;
        try {
            limit = Integer.parseInt(pageSize);
            int pageNumber = Integer.parseInt(page);
            offset = (limit * (pageNumber - 1));
        } catch (NumberFormatException e) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        if (limit <= 0 || offset < 0||limit>MAXIMUM_PAGESIZE) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        List<ShopUserDto> shopUserDtos =
                shopUserRepository.getAll(limit, offset)
                        .stream()
                        .map(shopUserModelConverter::toDto)
                        .collect(Collectors.toList());
        if (shopUserDtos.isEmpty()) {
            throw new NotFoundException("", ErrorCode.NO_USERS_FOUND);
        }
        return shopUserDtos;
    }

    /**
     * @see ShopUserService
     */
    @Override
    public ShopUserDto getByLogin(String login) {
        ShopUser shopUser = shopUserRepository.getByLogin(login);
        return shopUserModelConverter.toDto(shopUser);
    }

    /**
     * @see ShopUserService
     */
    @Override
    public ShopUserDto signUp(ShopUserDto shopUserDto) {
        shopUserValidator.validateUserForSignUp(shopUserDto);
        shopUserDto.setPassword(passwordEncoder.encode(shopUserDto.getPassword()));
        ShopUser shopUser = shopUserModelConverter.toModel(shopUserDto);
        shopUser.setRole(Role.USER);
        shopUserRepository.create(shopUser);
        return shopUserModelConverter.toDto(shopUser);
    }

    /**
     * @see ShopUserService
     */
    @Override
    public TokenDto authenticate(ShopUserCredentials credentials) {
        credentialsValidator.validateUserForLogin(credentials);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword()));
        } catch (DisabledException e) {
            throw new ValidatorException(credentials.getLogin(), ErrorCode.USER_BLOCKED);
        } catch (BadCredentialsException e) {
            throw new ValidatorException("", ErrorCode.WRONG_LOGIN_OR_PASS);
        }
        UserDetails userDetails = customerDetailsService.loadUserByUsername(credentials.getLogin());
        final String token = tokenUtil.generateToken(userDetails);
        Token resultToken = new Token();
        resultToken.setToken(token);
        String refreshToken = tokenUtil.generateRefreshToken(userDetails);
        TokenDto resultTokenDto = new TokenDto();
        resultTokenDto.setBearer(token);
        resultTokenDto.setRefresh(refreshToken);
        return resultTokenDto;
    }

    @Override
    public TokenDto refresh(TokenDto refreshToken) {
        String login = tokenUtil.getUsernameFromToken(refreshToken.getRefresh());
        UserDetails userDetails = customerDetailsService.loadUserByUsername(login);
        String token = tokenUtil.generateToken(userDetails);
        String resultRefreshToken = tokenUtil.generateRefreshToken(userDetails);
        TokenDto resultTokenDto = new TokenDto();
        resultTokenDto.setBearer(token);
        resultTokenDto.setRefresh(resultRefreshToken);
        return resultTokenDto;
    }
}
