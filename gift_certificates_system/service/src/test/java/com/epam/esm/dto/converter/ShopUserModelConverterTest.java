package com.epam.esm.dto.converter;

import com.epam.esm.dto.impl.ShopUserDto;
import com.epam.esm.model.ShopUser;
import com.epam.esm.model.role.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ShopUserModelConverterTest {
    private static final long ID = 1L;
    private static final String USER_NAME = "admin";
    private static final String USER_PASS = "admin";
    private static final String USER_ROLE = "admin";

    private ShopUser shopUser;
    private ShopUserDto shopUserDto;


    @InjectMocks
    private ShopUserModelConverter shopUserModelConverter;
    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {
        shopUser = new ShopUser();
        shopUser.setId(ID);
        shopUser.setName(USER_NAME);
        shopUser.setLogin(USER_NAME);
        shopUser.setPassword(USER_PASS);
        shopUser.setRole(Role.ADMINISTRATOR);

        shopUserDto = new ShopUserDto();
        shopUserDto.setId(ID);
        shopUserDto.setName(USER_NAME);
        shopUserDto.setLogin(USER_NAME);
        shopUserDto.setPassword(USER_PASS);
        shopUserDto.setRole("ADMINISTRATOR");
        Mockito.when(modelMapper.map(shopUser, ShopUserDto.class)).thenReturn(shopUserDto);
        Mockito.when(modelMapper.map(shopUserDto, ShopUser.class)).thenReturn(shopUser);
    }

    @Test
    public void testToDtoShouldReturnShopUserDto() {
        ShopUserDto actual = shopUserModelConverter.toDto(shopUser);
        Assert.assertEquals(shopUserDto, actual);
    }

    @Test
    public void testToModelShouldReturnShopUser() {
        ShopUser actual = shopUserModelConverter.toModel(shopUserDto);
        Assert.assertEquals(shopUser, actual);
    }
}
