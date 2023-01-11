package pl.trytek.easytrip.api.attraction.dto;

import java.io.Serial;
import java.io.Serializable;

public record CountryDto(

        Long id,

        String name

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}