package pl.trytek.easytrip.api.attraction.dto;

import pl.trytek.easytrip.data.domain.AttractionTypeEnum;

import javax.persistence.Column;

public record AttractionDetailsDto(

    Long id,

    String name,

    String city,

    Integer likes,

    AttractionTypeEnum type,

    String postalCode,

    String street,

    String buildingNumber,

    String apartmentNumber,

    String phoneNumber

){}
