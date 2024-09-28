package com.cuscatlan.payments.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up ModelMapper as a Spring bean.
 * ModelMapper is used for mapping between different object models
 * in the application, facilitating data transfer and transformation.
 */
@Configuration
public class MapperConfig {

    /**
     * Creates and configures a ModelMapper bean.
     *
     * @return a new instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
