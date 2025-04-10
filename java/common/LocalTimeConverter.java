package Offime.Offime.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalTime;

@Converter
public class LocalTimeConverter implements AttributeConverter<LocalTime, LocalTime> {

    @Override
    public LocalTime convertToDatabaseColumn(LocalTime attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.withNano(0);
    }

    @Override
    public LocalTime convertToEntityAttribute(LocalTime dbData) {
        return dbData;
    }
}