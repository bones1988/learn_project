package com.epam.esm.dto.converter;

import com.epam.esm.dto.DtoModelConverter;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.model.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of DtoModelConverter interface
 */
@Component
public class TagModelConverter implements DtoModelConverter<TagDto, Tag> {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * @see DtoModelConverter
     */
    public TagDto toDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    /**
     * @see DtoModelConverter
     */
    public Tag toModel(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }
}
