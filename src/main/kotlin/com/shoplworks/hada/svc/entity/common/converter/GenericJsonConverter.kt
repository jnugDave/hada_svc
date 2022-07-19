package com.shoplworks.hada.svc.entity.common.converter

import com.fasterxml.jackson.databind.ObjectMapper
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
abstract class GenericJsonConverter<T>(
    private val clazz: Class<T>
) : AttributeConverter<T, String?> {

    override fun convertToDatabaseColumn(model: T): String? {
        try {
            return ObjectMapper().writeValueAsString(model)
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message)
        }
    }

    override fun convertToEntityAttribute(jsonStr: String?): T? {
        try {
            if(jsonStr.isNullOrEmpty())
                return null

            return ObjectMapper().readValue(jsonStr, clazz)
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message)
        }
    }
}