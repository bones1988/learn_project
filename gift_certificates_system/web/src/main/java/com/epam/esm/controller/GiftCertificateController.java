package com.epam.esm.controller;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.PurchaseDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Gift certificate rest controller
 */
@RestController
@RequestMapping(value = "/certificates", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class GiftCertificateController {
    private GiftCertificateService giftCertificateService;
    private PurchaseService purchaseService;

    /**
     * Constructor of certificate controller
     *
     * @param giftCertificateService service which provides actions with certificates
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, PurchaseService purchaseService) {
        this.giftCertificateService = giftCertificateService;
        this.purchaseService = purchaseService;

    }

    /**
     * Method for searching certificate by params
     *
     * @param params params by which it will search and sort certificates
     * @return list of certificates according to params
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> getCertificateByParams(@RequestParam(required = false) Map<String, String> params) {
        HttpStatus status = HttpStatus.OK;
        List<GiftCertificateDto> resultList = giftCertificateService.getByParams(params);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        long lastPage = giftCertificateService.getLastPage(params);
        headers.add("Total", String.valueOf(lastPage));
        headers.add("Access-Control-Expose-Headers", "Total");
        return new ResponseEntity<List<GiftCertificateDto>>(resultList, headers, status);

//        return giftCertificateService.getByParams(params);
    }

    /**
     * Method for searching certificate by id
     *
     * @param id positive id of desirable certificate
     * @return gift certificate with desired id
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public GiftCertificateDto getCertificateById(@PathVariable("id") final Long id) {
        return giftCertificateService.getById(id);
    }

    /**
     * Method for saving certificate
     *
     * @param giftCertificateDto should valid full object for saving
     * @return saved certificate from database
     */
    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public GiftCertificateDto saveCertificate(@RequestBody(required = false) GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.save(giftCertificateDto);
    }

    /**
     * Method for deleting certificate by id
     *
     * @param id positive id of desirable certificate
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMINISTRATOR")
    public void deleteCertificate(@PathVariable("id") final Long id) {
        giftCertificateService.delete(id);
    }

    /**
     * method for handling bad request
     */
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Secured("ROLE_ADMINISTRATOR")
    public void missingPathVariableDelete() {
        throw new ValidatorException("", ErrorCode.MISSING_PATH_VARIABLE);
    }

    /**
     * method for handling bad request
     */
    @PutMapping
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Secured("ROLE_ADMINISTRATOR")
    public void missingPathVariablePut() {
        throw new ValidatorException("", ErrorCode.MISSING_PATH_VARIABLE);
    }

    /**
     * method for handling bad request
     */
    @PatchMapping
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Secured("ROLE_ADMINISTRATOR")
    public void missingPathVariablePatch() {
        throw new ValidatorException("", ErrorCode.MISSING_PATH_VARIABLE);
    }

    /**
     * Method for patching certificate by id
     *
     * @param id                 positive id of desirable certificate
     * @param giftCertificateDto object with fields you want to change.
     * @return gift changed certificate
     */
    @PatchMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_ADMINISTRATOR")
    public GiftCertificateDto patchCertificate(@PathVariable("id") final Long id,
                                               @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.patch(giftCertificateDto, id);
    }

    /**
     * Method for putting certificate by id
     *
     * @param id                 positive id of desirable certificate
     * @param giftCertificateDto object with all fields
     * @return new crtificate with old id
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_ADMINISTRATOR")
    public GiftCertificateDto putCertificate(@PathVariable("id") final Long id,
                                             @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.put(giftCertificateDto, id);
    }

    /**
     * @param id    id of certificate
     * @param price new price
     * @return changed certificate
     */
    @PatchMapping(value = "/{id}/price")
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_ADMINISTRATOR")
    public GiftCertificateDto changePrice(@PathVariable("id") final long id,
                                          @RequestBody(required = false) BigDecimal price) {
        return giftCertificateService.changePrice(id, price);
    }

    /**
     * @param id id of certificate
     * @return entity of purchase
     */
    @PostMapping(value = "/{id}/buy")
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public PurchaseDto buy(@PathVariable("id") final long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return purchaseService.buy(id, authentication);
    }
}
