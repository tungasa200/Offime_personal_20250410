package Offime.Offime.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, LocalDateTime> {

    @Override
    public LocalDateTime convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) return null;
        return attribute.truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(LocalDateTime dbData) {
        return dbData;
    }
}