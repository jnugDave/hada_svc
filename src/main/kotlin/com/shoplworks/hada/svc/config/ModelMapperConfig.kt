package com.shoplworks.hada.svc.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * packageName : com.shoplworks.hada.svc.config
 * fileName : ModelMapperConfig
 * author : dave
 * date : 2022/04/05
 * description : 최초등록
 */
@Configuration
class ModelMapperConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        modelMapper.configuration.isFieldMatchingEnabled = true
        modelMapper.configuration.isSkipNullEnabled = true
        modelMapper.configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        return modelMapper
    }
}