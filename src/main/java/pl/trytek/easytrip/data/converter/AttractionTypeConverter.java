package pl.trytek.easytrip.data.converter;

import pl.trytek.easytrip.data.domain.AttractionTypeEnum;

import javax.persistence.AttributeConverter;

public class AttractionTypeConverter implements AttributeConverter<AttractionTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(AttractionTypeEnum attractionType) {
        return attractionType.getCode();
    }

    @Override
    public AttractionTypeEnum convertToEntityAttribute(String code) {
        return AttractionTypeEnum.parse(code);
    }
}
