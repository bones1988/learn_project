package com.epam.esm.controller;

import com.epam.esm.config.ControllerTestConfig;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.token.TokenUtil;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ControllerTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagControllerTest {
    private static final long FIRST_ID = 1L;
    private static final String FIRST_NAME = "first";
    private TagDto tagDto;
    private UserDetails userDetails;
    private String token;


    private String uri;

    @Autowired
    private MockMvc mvc;
    @InjectMocks
    TagController tagController;
    @MockBean
    private TagService tagService;
    @Autowired
    private TokenUtil tokenUtil;

    @LocalServerPort
    private int port;


    @Before
    public void init() {
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(tagController));

        tagDto = new TagDto();
        tagDto.setId(FIRST_ID);
        tagDto.setName(FIRST_NAME);

        userDetails = new User("admin", "admin", Arrays.asList(new SimpleGrantedAuthority("ADMINISTRATOR")));
        token = tokenUtil.generateToken(userDetails);
    }

    @PostConstruct
    public void setPort() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void testGetTagByIdShouldReturnTagDto() {
        Mockito.when(tagService.getById(anyLong())).thenReturn(tagDto);

        TagDto actual = RestAssured.given()
                .when()
                .header("Authorization", "Bearer " + token)
                .get(uri + "/tags/1")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(TagDto.class);
        Assert.assertEquals(tagDto, actual);
    }

    @Test
    public void testGetTagByParamsShouldReturnTagDtoList() {
        Mockito.when(tagService.getByParams(any())).thenReturn(Arrays.asList(tagDto));

        TagDto[] actual = RestAssured.given()
                .when()
                .header("Authorization", "Bearer " + token)
                .get(uri + "/tags")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(TagDto[].class);
        Assert.assertEquals(new TagDto[]{tagDto}, actual);
    }

    @Test
    public void testSaveShouldReturnTagDto() {
        Mockito.when(tagService.save(tagDto)).thenReturn(tagDto);
        TagDto actual = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tagDto)
                .post(uri + "/tags")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TagDto.class);
        Assert.assertEquals(tagDto, actual);
    }

    @Test
    public void testDeleteTagShouldReturnStatusNoContent() {
        Mockito.when(tagService.deleteById(anyLong())).thenReturn(1);
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(uri + "/tags/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
