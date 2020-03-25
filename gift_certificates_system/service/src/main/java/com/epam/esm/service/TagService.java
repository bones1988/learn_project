package com.epam.esm.service;

import com.epam.esm.dto.impl.TagDto;

import java.util.List;
import java.util.Map;

/**
 * Interface for transfering data of certificates
 */
public interface TagService {
    /**
     * Method for getting all tags
     *
     * @return list of tag DTOs
     */
    List<TagDto> getByParams(Map<String, String> searchParams);

    /**
     * Method for getting tag by id
     *
     * @param id positive id of desired tag
     */
    TagDto getById(long id);

    /**
     * Method for saving tag
     *
     * @param tagdto tag DTO object to save
     */
    TagDto save(TagDto tagdto);

    /**
     * Method for deleting tag by id
     *
     * @param id positive id of deleting tag
     */
    int deleteById(long id);

    TagDto getPopularTag();
}
