package com.Ecole.demo.config.converter;

import com.Ecole.demo.entity.TypeConduite;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TypeConduiteAttributeConverter implements AttributeConverter<TypeConduite, String> {

    @Override
    public String convertToDatabaseColumn(TypeConduite attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public TypeConduite convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        // Tolérance: accepte BONNE, TRES BONNE, etc.
        return TypeConduite.parse(dbData);
    }
}
