package com.epam.esm.controller;

import com.epam.esm.config.ControllerTestConfig;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ControllerTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GiftCertificateControllerTest {
    private static final long FIRST_CERTIFICATE_ID = 1L;
    private static final String FIRST_CERTIFICATE_NAME = "first certificate";
    private static final String FIRST_CERTIFICATE_DESCRIPTION = "description first certificate";
    private static final BigDecimal FIRST_CERTIFICATE_PRICE = BigDecimal.ONE;
    private static final LocalDateTime FIRST_CERTIFICATE_DATE =
            LocalDateTime.of(1, Month.JANUARY, 1, 1, 1, 1, 1);
    private static final int FIRST_CERTIFICATE_DURATION = 1;
    private GiftCertificateDto giftCertificateDto;
    private String uri;
    private String token;
    private UserDetails userDetails;

    @Autowired
    private MockMvc mvc;
    @InjectMocks
    GiftCertificateController giftCertificateController;
    @MockBean
    private GiftCertificateService giftCertificateService;
    @MockBean
    private PurchaseService purchaseService;
    @Autowired
    private TokenUtil tokenUtil;

    @LocalServerPort
    private int port;


    @Before
    public void init() {
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(giftCertificateController));
        giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(FIRST_CERTIFICATE_ID);
        giftCertificateDto.setName(FIRST_CERTIFICATE_NAME);
        giftCertificateDto.setDescription(FIRST_CERTIFICATE_DESCRIPTION);
        giftCertificateDto.setPrice(FIRST_CERTIFICATE_PRICE);
        giftCertificateDto.setCreateDate(FIRST_CERTIFICATE_DATE);
        giftCertificateDto.setLastUpdateDate(FIRST_CERTIFICATE_DATE);
        giftCertificateDto.setDuration(FIRST_CERTIFICATE_DURATION);
        giftCertificateDto.setActive(true);

        userDetails = new User("admin", "admin", Arrays.asList(new SimpleGrantedAuthority("ADMINISTRATOR")));
        token = tokenUtil.generateToken(userDetails);
    }

    @PostConstruct
    public void setPort() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void testGetCertificateByIdShouldReturnFirstCertificateDto() {
        Mockito.when(giftCertificateService.getById(anyLong())).thenReturn(giftCertificateDto);

        GiftCertificateDto actual = RestAssured.given()
                .when()
                .get(uri + "/certificates/1")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(GiftCertificateDto.class);
        Assert.assertEquals(giftCertificateDto, actual);
    }

    @Test
    public void testGetCertificateByParamsShouldReturnList() {
        Mockito.when(giftCertificateService.getByParams(any())).thenReturn(Arrays.asList(giftCertificateDto));
        GiftCertificateDto[] actual = RestAssured.given()
                .when()
                .get(uri + "/certificates")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(GiftCertificateDto[].class);
        Assert.assertEquals(new GiftCertificateDto[]{giftCertificateDto}, actual);
    }

    @Test
    public void testDeleteCertificateShouldReturnStatusNoContent() {
        Mockito.when(giftCertificateService.delete(anyLong())).thenReturn(giftCertificateDto);
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(uri + "/certificates/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testSaveShouldReturnGiftCertificateDto() {
        Mockito.when(giftCertificateService.save(giftCertificateDto)).thenReturn(giftCertificateDto);
        GiftCertificateDto actual = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(giftCertificateDto)
                .post(uri + "/certificates/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(GiftCertificateDto.class);
        Assert.assertEquals(giftCertificateDto, actual);
    }

    @Test
    public void testPutShouldReturnGiftCertificateDto() {
        Mockito.when(giftCertificateService.put(giftCertificateDto, 1)).thenReturn(giftCertificateDto);
        GiftCertificateDto actual = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(giftCertificateDto)
                .put(uri + "/certificates/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(GiftCertificateDto.class);
        Assert.assertEquals(giftCertificateDto, actual);
    }

    @Test
    public void testPatchShouldReturnGiftCertificateDto() {
        Mockito.when(giftCertificateService.patch(giftCertificateDto, 1)).thenReturn(giftCertificateDto);
        GiftCertificateDto actual = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(giftCertificateDto)
                .patch(uri + "/certificates/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(GiftCertificateDto.class);
        Assert.assertEquals(giftCertificateDto, actual);
    }
}
