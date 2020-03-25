package com.epam.esm.service.impl;

import com.epam.esm.dto.converter.GiftCertificateModelConverter;
import com.epam.esm.dto.converter.TagModelConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.filter.AbstractFilter;
import com.epam.esm.repository.filter.GiftCertificateFilter;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.CertificateFilterMaker;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @see GiftCertificateService
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateRepositoryImpl giftCertificateRepository;
    private GiftCertificateModelConverter giftCertificateModelConverter;
    private TagModelConverter tagModelConverter;
    private TagService tagService;
    private CertificateValidator certificateValidator;
    private CertificateFilterMaker certificateFilterMaker;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepositoryImpl giftCertificateRepository, GiftCertificateModelConverter giftCertificateModelConverter,
                                      TagService tagService, TagModelConverter tagModelConverter, CertificateValidator certificateValidator, CertificateFilterMaker certificateFilterMaker) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateModelConverter = giftCertificateModelConverter;
        this.tagService = tagService;
        this.tagModelConverter = tagModelConverter;
        this.certificateValidator = certificateValidator;
        this.certificateFilterMaker = certificateFilterMaker;
    }

    /**
     * @see GiftCertificateService
     */
    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
        GiftCertificate giftCertificate = giftCertificateModelConverter.toModel(giftCertificateDto);
        giftCertificate.setTags(checkAndSaveAndReturnValidTagSet(giftCertificate.getTags()));
        giftCertificate.setActive(true);
        giftCertificate.setId(giftCertificateRepository.add(giftCertificate));
        return giftCertificateModelConverter.toDto(giftCertificate);
    }

    /**
     * @see GiftCertificateService
     */
    @Override
    public GiftCertificateDto delete(long id) {
        AbstractFilter filter = new GiftCertificateFilter();
        filter.setId(id);
        GiftCertificate giftCertificate = giftCertificateRepository
                .query(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.valueOf(id), ErrorCode.CERTIFICATE_NOT_FOUND_BY_ID));
        giftCertificate.setActive(false);
        return giftCertificateModelConverter.toDto(giftCertificateRepository.update(giftCertificate));
    }

    /**
     * @see GiftCertificateService
     */
    @Override
    @Transactional
    public GiftCertificateDto put(GiftCertificateDto giftCertificateDto, long id) {
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
        giftCertificateDto.setId(id);
        GiftCertificate giftCertificate = giftCertificateModelConverter.toModel(giftCertificateDto);
        giftCertificate.setActive(true);
        Set<TagDto> tagDtoSet = giftCertificateDto.getTags();
        giftCertificate.setTags(
                tagDtoSet.stream()
                        .map(tagModelConverter::toModel)
                        .collect(Collectors.toSet())
        );
        giftCertificate.setTags(checkAndSaveAndReturnValidTagSet(giftCertificate.getTags()));
        try {
            giftCertificateRepository.update(giftCertificate);
        } catch (Exception e) {
            throw new NotFoundException(String.valueOf(id), ErrorCode.ERROR_UPDATING_CERTIFICATE);
        }
        return getById(id);
    }

    /**
     * @see GiftCertificateService
     */
    @Override
    public List<GiftCertificateDto> getByParams(Map<String, String> params) {
        GiftCertificateFilter filter = certificateFilterMaker.makeFilter(params);
        List<GiftCertificateDto> resultList = giftCertificateRepository
                .query(filter)
                .stream()
                .map(giftCertificateModelConverter::toDto)
                .collect(Collectors.toList());
        if (resultList.isEmpty()) {
            throw new NotFoundException("", ErrorCode.CERTIFICATE_NOT_FOUND);
        }
        return resultList;
    }

    /**
     * @see GiftCertificateService
     */
    public GiftCertificateDto getById(long id) {
        GiftCertificateFilter filter = new GiftCertificateFilter();
        filter.setId(id);
        return giftCertificateRepository
                .query(filter)
                .stream()
                .findFirst()
                .map(giftCertificateModelConverter::toDto)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id), ErrorCode.CERTIFICATE_NOT_FOUND_BY_ID));
    }

    private Set<Tag> checkAndSaveAndReturnValidTagSet(Set<Tag> tagSet) {
        return tagSet
                .stream()
                .map(tag -> tagModelConverter.toModel(tagService.save(tagModelConverter.toDto(tag))))
                .collect(Collectors.toSet());
    }

    /**
     * @see GiftCertificateService
     */
    @Override
    public GiftCertificateDto changePrice(long id, BigDecimal price) {
        GiftCertificateDto giftCertificateDto = getById(id);
        giftCertificateDto.setPrice(price);
        certificateValidator.validateCertificateForAdd(giftCertificateDto);
        GiftCertificate giftCertificate = giftCertificateModelConverter.toModel(giftCertificateDto);
        giftCertificateRepository.update(giftCertificate);
        return giftCertificateModelConverter.toDto(giftCertificate);
    }

    /**
     * @see GiftCertificateService
     */
    @Override
    public GiftCertificateDto patch(GiftCertificateDto giftCertificateDto, long id) {
        AbstractFilter filter = new GiftCertificateFilter();
        filter.setId(id);
        GiftCertificate existCertificate = giftCertificateRepository
                .query(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.valueOf(id), ErrorCode.CERTIFICATE_NOT_FOUND_BY_ID));
        certificateValidator.validateCertificateForPatch(giftCertificateDto);
        GiftCertificate inputCertificate = giftCertificateModelConverter.toModel(giftCertificateDto);
        if (inputCertificate.getName() != null) {
            existCertificate.setName(inputCertificate.getName());
        }
        if (inputCertificate.getDescription() != null) {
            existCertificate.setDescription(inputCertificate.getDescription());
        }
        if (inputCertificate.getPrice() != null) {
            existCertificate.setPrice(inputCertificate.getPrice());
        }
        if (inputCertificate.getDuration() != 0) {
            existCertificate.setDuration(inputCertificate.getDuration());
        }
        if (inputCertificate.isActive() != null) {
            existCertificate.setActive(inputCertificate.isActive());
        }
        if (!inputCertificate.getTags().isEmpty()) {
            existCertificate.setTags(inputCertificate.getTags());
        }
        try {
            giftCertificateRepository.update(existCertificate);
        } catch (Exception e) {
            throw new NotFoundException(String.valueOf(id), ErrorCode.ERROR_UPDATING_CERTIFICATE);
        }
        return getById(id);
    }

    @Override
    public long getLastPage(Map<String, String> params) {
        GiftCertificateFilter filter = certificateFilterMaker.makeFilter(params);
        return giftCertificateRepository.countCertificateByParams(filter);
    }
}
