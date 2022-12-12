package pl.trytek.easytrip.api.attraction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.trytek.easytrip.data.domain.AttractionTypeEnum;


public record AttractionDetailsDto(

        @Schema(name = "Id atrakcji")
        Long id,

        @Schema(name = "Nazwa atrakcji")
        String name,

        @Schema(name = "Miejscowosc")
        String city,

        @Schema(name = "Ilość polubien")
        Integer likes,

        @Schema(name = "Typ atrakcji")
        AttractionTypeEnum type,

        @Schema(name = "Kod pocztowy")
        String postalCode,

        @Schema(name = "Ulica")
        String street,

        @Schema(name = "nr_budynku")
        String buildingNumber,

        @Schema(name ="nr_lokalu")
        String apartmentNumber,

        @Schema(name ="nr_telefonu")
        String phoneNumber,

        @Schema(name ="opis")
        String description,

        @Schema(name ="czy ulubiona")
        Boolean favourite,

        @Schema(name ="czy polubiona")
        Boolean like

) {
}
