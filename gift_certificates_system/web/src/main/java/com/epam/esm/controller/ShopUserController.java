package com.epam.esm.controller;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.PurchaseDto;
import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.dto.impl.TokenDto;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.ShopUserService;
import com.epam.esm.token.ShopUserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User rest controller
 */
@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ShopUserController {
    private ShopUserService shopUserService;
    private PurchaseService purchaseService;

    @Autowired
    public ShopUserController(ShopUserService shopUserService, PurchaseService purchaseService) {
        this.shopUserService = shopUserService;
        this.purchaseService = purchaseService;
    }

    /**
     * @return list of all users
     */
    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR"})
    public List<ShopUserDto> getAll(@RequestParam(defaultValue = "5") String pageSize,
                                    @RequestParam(defaultValue = "1") String page) {
        return shopUserService.getAll(pageSize, page);
    }

    /**
     * @return entity of current user
     */
    @GetMapping(value = "/personalArea/purchases")
    @Secured("ROLE_USER")
    public List<PurchaseDto> showPersonalPurchases(@RequestParam(defaultValue = "5") String pageSize,
                                                   @RequestParam(defaultValue = "1") String page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        long id = shopUserService.getByLogin(login).getId();
        return purchaseService.getUserPurchases(id, pageSize, page);
    }

    @GetMapping(value = "/personalArea/certificates")
    @Secured("ROLE_USER")
    public List<GiftCertificateDto> showPersonalCertificates(@RequestParam(defaultValue = "5") String pageSize,
                                                             @RequestParam(defaultValue = "1") String page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        long id = shopUserService.getByLogin(login).getId();
        return purchaseService.getUserCertificates(id, pageSize, page);
    }

    @GetMapping(value = "/personalArea")
    @Secured({"ROLE_USER", "ROLE_ADMINISTRATOR"})
    public ShopUserDto showMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return shopUserService.getByLogin(login);
    }

    /**
     * @param id id of desirable user
     * @return list of purchases of user
     */
    @GetMapping(value = "/{id}/purchases")
    @Secured({"ROLE_ADMINISTRATOR"})
    public List<PurchaseDto> showUsersPurchases(@PathVariable long id,
                                                @RequestParam(defaultValue = "5") String pageSize,
                                                @RequestParam(defaultValue = "1") String page) {
        return purchaseService.getUserPurchases(id, pageSize, page);
    }

    /**
     * @param id users id
     * @return certificates of user
     */
    @GetMapping(value = "/{id}/certificates")
    @Secured({"ROLE_ADMINISTRATOR"})
    public List<GiftCertificateDto> showUsersCertificates(@PathVariable long id,
                                                          @RequestParam(defaultValue = "5") String pageSize,
                                                          @RequestParam(defaultValue = "1") String page) {
        return purchaseService.getUserCertificates(id, pageSize, page);
    }

    /**
     * @param shopUserDto users parameters
     * @return registered user
     */
    @PostMapping(value = "/registration")
    public ShopUserDto signUp(@RequestBody(required = false) ShopUserDto shopUserDto) {
        return shopUserService.signUp(shopUserDto);
    }

    /**
     * @param credentials username and password
     * @return token for requests
     */
    @PostMapping(value = "/authentication")
    public TokenDto createAuthenticationToken(@RequestBody(required = false) ShopUserCredentials credentials) {
        return shopUserService.authenticate(credentials);
    }

    @PostMapping(value = "/refresh")
    public TokenDto refreshToken(@RequestBody(required = false) TokenDto refreshTokenDto) {
        return shopUserService.refresh(refreshTokenDto);
    }
}
