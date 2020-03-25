package com.epam.esm.dto.converter;

import com.epam.esm.dto.DtoModelConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation if dtomodelconverter
 */
@Component
public class GiftCertificateModelConverter implements DtoModelConverter<GiftCertificateDto, GiftCertificate> {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * @see DtoModelConverter
     */
    public GiftCertificateDto toDto(GiftCertificate giftCertificate) {
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    /**
     * @see DtoModelConverter
     */
    public GiftCertificate toModel(GiftCertificateDto giftcertificateDto) {
        return modelMapper.map(giftcertificateDto, GiftCertificate.class);
    }
}
