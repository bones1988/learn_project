package com.epam.esm.dto.converter;

import com.epam.esm.dto.DtoModelConverter;
import com.epam.esm.dto.impl.PurchaseDto;
import com.epam.esm.model.Purchase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of DtoModelConverter interface
 */
@Component
public class PurchaseModelConverter implements DtoModelConverter<PurchaseDto, Purchase> {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * @see DtoModelConverter
     */
    public PurchaseDto toDto(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDto.class);
    }

    /**
     * @see DtoModelConverter
     */
    public Purchase toModel(PurchaseDto purchaseDto) {
        return modelMapper.map(purchaseDto, Purchase.class);
    }
}
