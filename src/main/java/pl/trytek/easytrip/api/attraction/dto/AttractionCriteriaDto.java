package pl.trytek.easytrip.api.attraction.dto;

import pl.trytek.easytrip.data.domain.AttractionTypeEnum;

import java.util.List;

public record AttractionCriteriaDto(
        String country,

        String street,
        String name,
        String city,

        List<AttractionTypeEnum> types
) {
}
