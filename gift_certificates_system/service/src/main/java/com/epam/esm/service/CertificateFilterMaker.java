package com.epam.esm.service;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * filter makes class for certificate
 */
@Component
public class CertificateFilterMaker {
    private static final String SPLIT_PARAMS_SIGN = ",";
    private static final int MAXIMUM_PAGESIZE = 50;

    /**
     * @param params params for search
     * @return filter according to params
     */
    public GiftCertificateFilter makeFilter(Map<String, String> params) {
        GiftCertificateFilter giftCertificateFilter = new GiftCertificateFilter();
        int pageSize = 5;
        int page = 1;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                switch (entry.getKey()) {
                    case "pageSize":
                        pageSize = Integer.parseInt(entry.getValue());
                        if(pageSize>MAXIMUM_PAGESIZE) {
                            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
                        }
                        break;
                    case "page":
                        page = Integer.parseInt(entry.getValue());
                        break;
                    case "name":
                        giftCertificateFilter.setNameParts(makeParamsList(entry.getValue()));
                        break;
                    case "description":
                        giftCertificateFilter.setDescParts(makeParamsList(entry.getValue()));
                        break;
                    case "tag":
                        giftCertificateFilter.setTagNames(makeTagNamesList(entry.getValue()));
                        break;
                    case "sort":
                        giftCertificateFilter.setSortParams(makeSortList(entry.getValue()));
                        break;
                    default:
                        throw new ValidatorException(entry.getKey(), ErrorCode.WRONG_SEARCH_PARAM);
                }
            } catch (NumberFormatException e) {
                throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
            }
        }
        if (pageSize<=0||page<=0) {
            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
        }
        giftCertificateFilter.setLimit(pageSize);
        giftCertificateFilter.setOffset((pageSize * (page - 1)));
        return giftCertificateFilter;
    }


    private List<String> makeParamsList(String params) {
        return Stream.of(params.split(SPLIT_PARAMS_SIGN))
                .map(s -> "'%" + s + "%'")
                .collect(Collectors.toList());
    }

    private List<String> makeTagNamesList(String params) {
        return
                Stream.of(params.split(SPLIT_PARAMS_SIGN))
                        .collect(Collectors.toList());
    }

    private List<String> makeSortList(String params) {
        String[] sortArray = params.split(SPLIT_PARAMS_SIGN);
        List<String> sortList = new ArrayList<>();
        for (String sortString : sortArray) {
            switch (sortString) {
                case "id":
                    sortList.add("certificate.id");
                    break;
                case "iddesc":
                    sortList.add("certificate.id desc");
                    break;
                case "name":
                    sortList.add("certificate.name");
                    break;
                case "namedesc":
                    sortList.add("certificate.name desc");
                    break;
                case "description":
                    sortList.add("certificate.description");
                    break;
                case "descriptiondesc":
                    sortList.add("certificate.description desc");
                    break;
                case "price":
                    sortList.add("certificate.price");
                    break;
                case "pricedesc":
                    sortList.add("certificate.price desc");
                    break;
                case "create":
                    sortList.add("certificate.create_date");
                    break;
                case "createdesc":
                    sortList.add("certificate.create_date desc");
                    break;
                case "update":
                    sortList.add("certificate.update_date");
                    break;
                case "updatedesc":
                    sortList.add("certificate.update_date desc");
                    break;
                case "duration":
                    sortList.add("certificate.duration");
                    break;
                case "durationdesc":
                    sortList.add("certificate.duration desc");
                    break;
                default:
                    throw new ValidatorException(sortString, ErrorCode.INCORRECT_SORT_PARAM);
            }
        }
        return sortList;
    }
}
