package com.epam.esm.dto.converter;

import com.epam.esm.dto.DtoModelConverter;
import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.model.ShopUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of DtoModelConverter interface
 */
@Component
public class ShopUserModelConverter implements DtoModelConverter<ShopUserDto, ShopUser> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShopUserDto toDto(ShopUser shopUser) {
        return modelMapper.map(shopUser, ShopUserDto.class);
    }

    @Override
    public ShopUser toModel(ShopUserDto shopUserDto) {
        return modelMapper.map(shopUserDto, ShopUser.class);
    }
}
