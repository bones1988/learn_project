package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.TagModelConverter;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ModelRepository;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.TagFilter;
import com.epam.esm.service.TagFilterMaker;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @see TagService
 */
@Service
public class TagServiceImpl implements TagService {
    private ModelRepository<Tag> tagRepository;
    private TagModelConverter tagModelConverter;
    private TagValidator tagValidator;
    private TagFilterMaker tagFilterMaker;

    @Autowired
    public TagServiceImpl(ModelRepository<Tag> tagRepository, TagModelConverter tagModelConverter,
                          TagFilterMaker tagFilterMaker, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.tagModelConverter = tagModelConverter;
        this.tagValidator = tagValidator;
        this.tagFilterMaker = tagFilterMaker;
    }

    /**
     * @see TagService
     */
    @Override
    public List<TagDto> getByParams(Map<String, String> searchParams) {
        TagFilter tagFilter = tagFilterMaker.makeFilter(searchParams);
        List<TagDto> resultList = tagRepository
                .query(tagFilter)
                .stream()
                .map(tagModelConverter::toDto)
                .collect(Collectors.toList());
        if (resultList.isEmpty()) {
            throw new NotFoundException("", ErrorCode.TAGS_NOT_FOUND);
        }
        return resultList;
    }


    /**
     * @see TagService
     */
    @Override
    public TagDto save(TagDto tagdto) {
        tagValidator.validateTagDto(tagdto);
        TagFilter tagFilter = new TagFilter();
        tagFilter.setName(tagdto.getName());
        Tag tag = tagModelConverter.toModel(tagdto);
        try {
            List<Tag> tagList = tagRepository.query(tagFilter);
            if (tagList.isEmpty()) {
                tagdto.setId(tagRepository.add(tag));
                return tagdto;
            } else return tagModelConverter.toDto(tagList.get(0));
        } catch (Exception e) {
            throw new NotFoundException(ErrorCode.ERROR_SAVING_TAG);
        }
    }

    /**
     * @see TagService
     */
    @Override
    public TagDto getById(long id) {
        TagFilter tagFilter = new TagFilter();
        tagFilter.setId(id);
        return tagRepository
                .query(tagFilter)
                .stream()
                .findFirst()
                .map(tagModelConverter::toDto)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id), ErrorCode.TAGS_NOT_FOUND_BY_ID));
    }

    /**
     * @see TagService
     */
    @Override
    public int deleteById(long id) {
        int result;
        result = tagRepository.remove(id);
        if (result == 0) {
            throw new NotFoundException(String.valueOf(id), ErrorCode.ERROR_DELETING_TAG);
        }
        return result;
    }

    @Override
    public TagDto getPopularTag() {
        AbstractFilter tagFilter = new TagFilter();
        tagFilter.setPopularTagTrigger(true);
        return tagRepository.query(tagFilter)
                .stream()
                .map(tagModelConverter::toDto)
                .findFirst()
                .orElseThrow(()-> new NotFoundException("", ErrorCode.TAGS_NOT_FOUND));
    }
}
