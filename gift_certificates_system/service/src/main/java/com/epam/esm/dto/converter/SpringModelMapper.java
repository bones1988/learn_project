package com.epam.esm.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class for creating model mapper bean
 */
@Configuration
public class SpringModelMapper {

    /**
     * method for creating model mapper bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
