package com.epam.esm.service;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.filter.TagFilter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class for making filter for tags
 */
@Component
public class TagFilterMaker {
    private static final int MAXIMUM_PAGESIZE = 50;

    /**
     * @param paramsMap input params for filter
     * @return filter
     */
    public TagFilter makeFilter(Map<String, String> paramsMap) {
        TagFilter tagFilter = new TagFilter();
        int pageSize = 5;
        int page = 1;
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            try {
                switch (entry.getKey()) {
                    case "name":
                        tagFilter.setName((entry.getValue()));
                        break;
                    case "pageSize":
                        pageSize = Integer.parseInt(entry.getValue());
                        if(pageSize>MAXIMUM_PAGESIZE) {
                            throw new ValidatorException("", ErrorCode.WRONG_SEARCH_PARAMS);
                        }
                        break;
                    case "page":
                        page = Integer.parseInt(entry.getValue());
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
        tagFilter.setLimit(pageSize);
        tagFilter.setOffset((pageSize * (page - 1)));
        return tagFilter;
    }
}
