package com.epam.esm.service.impl;


import com.epam.esm.model.ShopUser;
import com.epam.esm.repository.ShopUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @see UserDetailsService
 */
@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ShopUserRepository shopUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ShopUser shopUser = shopUserRepository.getByLogin(username);
        return new User(shopUser.getLogin(), shopUser.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + shopUser.getRole().getRole())));
    }
}
